package com.example.unscramble

import com.example.unscramble.data.UserData
import com.example.unscramble.data.UserInfo
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.impl.annotations.SpyK
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.mockkStatic
import io.mockk.spyk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test

object MockKStaticDemoTest {


    /**
     * 註解的方式
     * **/

    /**
     * 普通 Mock，調用所有Function都要先調用「every」，否則會拋出異常。
     * **/
    @MockK
    lateinit var mockUserInfo : UserInfo


    /**
     * Relaxed模式 Mock， 調用所有Function都會返回默認值，除非調用「every」
     * **/
    @RelaxedMockK
    lateinit var relaxedMockKUserInfo: UserInfo

    /**
     * Spy， 調用的所有Function都會執行真實的Code，除非調用「every」
     * **/
    @SpyK
    var spyMockKUserInfo: UserInfo = UserInfo("li", "123")

    @JvmStatic
    @BeforeAll
    fun beforeInit() {
        /**
         * 通過註解方式生成Mock，這行是必須的
         * relaxUnitFun，代表所有沒有return的Function都可以正常調用，不需要先調用「every」
         * **/
        MockKAnnotations.init(this, relaxUnitFun = true)
    }


    @Test
    fun normalMockTest() {
        val mockUserInfo = mockk<UserInfo>()
        val userData = UserData("1", 0, userInfo = mockUserInfo)
        val userData2 = UserData("2")

        assertEquals(null, userData2.getUserInfoSummary())


        // Mock mockUserInfo.summary() 放回 `summary`
        every { mockUserInfo.summary() } returns "summary"

        assertEquals("summary", userData.getUserInfoSummary())
    }


    @Test
    fun relaxedMockTest() {
        val mockUserInfo = mockk<UserInfo>(relaxed = true)
        val userData = UserData("1", 0, userInfo = mockUserInfo)

        //如果是普通的mockk，這裡會報錯，因為沒有先調用 every， relaxed 模式下，會返回默認值
        assertEquals("", userData.getUserInfoSummary())

        every { mockUserInfo.summary() } returns "summary"
        assertEquals("summary", userData.getUserInfoSummary())
    }

    @Test
    fun spyMockTest() {

        //recordPrivateCalls 記錄私有變量行為
        val spyUserInfo = spyk(UserInfo("li", "li"), recordPrivateCalls = true)
        val spyUserData = UserData("1", 0, userInfo = spyUserInfo)

        assertEquals("li -- li", spyUserData.getUserInfoSummary())

        // Mock 私有Function
        every { spyUserInfo["callUserName"]() } returns "userName"
        every { spyUserInfo["callPhoneNum"]() } returns "phoneNum"

        assertEquals("userName -- phoneNum", spyUserData.getUserInfoSummary())


        val mockUserInfo = mockk<UserInfo>(relaxed = true)
        val mockUserData = UserData("1", 0, userInfo = mockUserInfo)

        every { mockUserInfo["callUserName"]() } returns "userName"
        every { mockUserInfo["callPhoneNum"]() } returns "phoneNum"

        // relaxed 模式下，會返回默認值，所以mockUserInfo.summary，始終返回 ""
        assertEquals("", mockUserData.getUserInfoSummary())
    }

    @Test
    fun objectMockTest() {

        val msg = "test"

        mockkObject(ObjectStatic)

        every { ObjectStatic.callObjectFunction(any()) } returns "mock"

        assertEquals("mock", ObjectStatic.callObjectFunction(msg))

        mockkStatic(ObjectStatic::class)

        every { ObjectStatic.callStaticFunc(any()) } returns "mock"

        assertEquals("mock", ObjectStatic.callStaticFunc(msg))


    }

    @Test
    fun companionObjectMockTest() {

        val msg = "test"

        mockkObject(CompanionObjectStatic.Companion)

        every { CompanionObjectStatic.callCompanionFunction(any()) } returns "mock"

        assertEquals("mock", CompanionObjectStatic.callCompanionFunction(msg))

        /**
         * 對於Companion Object 不用 mockkStatic，也能調用JvmStatic Function，和 Object 有不一樣
         * **/
        //mockkStatic(CompanionObjectStatic.Companion::class)

        every { CompanionObjectStatic.callCompanionStaticFunc(any()) } returns "mock"

        assertEquals("mock", CompanionObjectStatic.callCompanionStaticFunc(msg))

    }



}