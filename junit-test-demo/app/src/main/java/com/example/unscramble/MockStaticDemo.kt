package com.example.unscramble

class CompanionObjectStatic {

    companion object {

        fun callCompanionFunction(msg : String) : String {
            return msg
        }


        @JvmStatic
        fun callCompanionStaticFunc(msg : String) : String {
            return "static-$msg"
        }
    }

}

object ObjectStatic {

    fun callObjectFunction(msg : String) : String {
        return msg
    }


    @JvmStatic
    fun callStaticFunc(msg : String) : String {
        return "static-$msg"
    }



}