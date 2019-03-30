object SoLangConfiguration {

    var codeBlockIndices = Indices.START_FROM_1
//    var lineIndicies = Indices.START_FROM_1 TODO implement

    enum class Indices(val value: Int){
        START_FROM_0(0),
        START_FROM_1(1)
    }
}