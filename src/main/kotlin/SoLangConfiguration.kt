object SoLangConfiguration {

    var codeBlockIndices = Indices.START_FROM_1
//    var lineIndicies = Indices.START_FROM_1 TODO implement

    var soLangMode = SoLangMode.SAFE

    enum class Indices(val value: Int){
        START_FROM_0(0),
        START_FROM_1(1)
    }

    enum class SoLangMode{
        UNSAFE,
        SAFE,
        PRINT
    }
}