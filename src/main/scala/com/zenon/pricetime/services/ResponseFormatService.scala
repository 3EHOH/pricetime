package com.zenon.pricetime.services

object ResponseFormatService {
  def setResponseFormat(queryParam: Option[String]) : String = {
      queryParam match {
        case Some(paramVal) if paramVal == "xml" => paramVal
        case _ => "json"
      }
  }

  def determineResponseFormat(queryParam: Map[String,Seq[String]]) : String = {
    var firstQueryParamVal : Option[String] = None
    if(!queryParam.isEmpty){
      val (queryParamKey, queryParamVals) = queryParam.head
      firstQueryParamVal = queryParamVals.headOption
    }
    setResponseFormat(firstQueryParamVal)
  }
}
