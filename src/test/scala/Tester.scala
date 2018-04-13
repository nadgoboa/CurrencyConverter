import currency.IncomingDataProtocol._
import currency.QueryProtocol._
import currency.AnswerProtocol._
import currency.OutcomingDataProtocol._
import currency.{Answer, IncomingData, OutcomingData, Query}
import org.scalatest.{FreeSpec, Matchers}
import scalaj.http.{Http, HttpOptions}
import spray.json._

class Tester extends FreeSpec with Matchers{
  def post(jsonStr: String) = {
    Http("http://localhost:8080").postData(jsonStr)
      .header("Content-Type", "application/json")
      .header("Charset", "UTF-8")
      .option(HttpOptions.readTimeout(10000)).asString
  }


    val input1 = IncomingData[Query](List(Query("EUR","RUB",1),
                                  Query("USD","RUB",1)))
    val answer1 = post(input1.toJson.prettyPrint)
    val output1 = answer1.body.parseJson.convertTo[OutcomingData[Answer]]

  "Test 1.1" in {
    answer1.code should be (200)
  }

  "Test 1.2" in {
    input1.data.length should be (output1.data.length)
  }

  "Test 1.3" in {
    for (n <- List.range(0, input1.data.length)) {
      input1.data(n).currencyFrom should be(output1.data(n).currencyFrom)
      input1.data(n).currencyTo should be(output1.data(n).currencyTo)
      input1.data(n).valueFrom should be(output1.data(n).valueFrom)
    }
  }

  val input2 = IncomingData[Query](List(Query("MDL","RUB",500),
                                Query("USD","EUR",5),
                                Query("USD","RUB",12),
                                Query("RUB","RUB",8000),
                                Query("EUR","USD",732)))
  val answer2 = post(input2.toJson.prettyPrint)
  val output2 = answer2.body.parseJson.convertTo[OutcomingData[Answer]]

  "Test 2.1" in {
    answer2.code should be (200)
  }

  "Test 2.2" in {
    input2.data.length should be (output2.data.length)
  }

  "Test 2.3" in {
    for (n <- List.range(0, input2.data.length)) {
      input2.data(n).currencyFrom should be (output2.data(n).currencyFrom)
      input2.data(n).currencyTo should be (output2.data(n).currencyTo)
      input2.data(n).valueFrom should be (output2.data(n).valueFrom)
    }
  }

  val input3 = IncomingData[Query](List(Query("EUR","RUB",200),
                                Query("RUB","USD",515.34),
                                Query("USD","RUB",1000),
                                Query("RUB","EUR",35000000),
                                Query("EUR","EUR",1),
                                Query("EUR","RUB",5),
                                Query("RUB","USD",120000),
                                Query("USD","RUB",50.7),
                                Query("RUB","EUR",2.03),
                                Query("EUR","EUR",88500)))
  val answer3 = post(input3.toJson.prettyPrint)
  val output3 = answer3.body.parseJson.convertTo[OutcomingData[Answer]]

  "Test 3.1" in {
    answer3.code should be (200)
  }

  "Test 3.2" in {
    input3.data.length should be (output3.data.length)
  }

  "Test 3.3" in {
    for (n <- List.range(0, input3.data.length)) {
      input3.data(n).currencyFrom should be (output3.data(n).currencyFrom)
      input3.data(n).currencyTo should be (output3.data(n).currencyTo)
      input3.data(n).valueFrom should be (output3.data(n).valueFrom)
    }
  }

  val input4 = IncomingData[Query](List())
  val answer4 = post(input4.toJson.prettyPrint)
  val output4 = answer4.body.parseJson.convertTo[OutcomingData[Answer]]

  "Test 4.1" in {
    answer4.code should be (200)
  }

  "Test 4.2" in {
    input4.data.length should be (output4.data.length)
  }

  "Test 4.3" in {
    for (n <- List.range(0, input4.data.length)) {
      input4.data(n).currencyFrom should be (output4.data(n).currencyFrom)
      input4.data(n).currencyTo should be (output4.data(n).currencyTo)
      input4.data(n).valueFrom should be (output4.data(n).valueFrom)
    }
  }

  val input5 = IncomingData[Query](List(Query("USD","RUB",3000)))
  val answer5 = post(input5.toJson.prettyPrint)
  val output5 = answer5.body.parseJson.convertTo[OutcomingData[Answer]]

  "Test 5.1" in {
    answer5.code should be (200)
  }

  "Test 5.2" in {
    input5.data.length should be (output5.data.length)
  }

  "Test 5.3" in {
    for (n <- List.range(0, input5.data.length)) {
      input5.data(n).currencyFrom should be (output5.data(n).currencyFrom)
      input5.data(n).currencyTo should be (output5.data(n).currencyTo)
      input5.data(n).valueFrom should be (output5.data(n).valueFrom)
    }
  }

  val answer6 = post("""{"data": [{"currencyFrom": "RUB","currencyTo": "WRONG!","valueFrom": 15.65}]}""".parseJson.prettyPrint)
  val output6 = answer6.body.parseJson.convertTo[OutcomingData[Answer]]

  "Test 6.1" in {
    answer6.code should be (200)
  }

  "Test 6.2" in {
    output6.data.length should be (0)
  }

  "Test 6.3" in {
    output6.errorCode should be (1)
  }

  val answer7 = post("""{"data": [{"currencyFrom": "RUB","currencyTo": "USD","SOME INCORRECT FIELD NAME": 15.65}]}""".parseJson.prettyPrint)
  val output7 = answer7.body.parseJson.convertTo[OutcomingData[Answer]]

  "Test 7.1" in {
    answer7.code should be (200)
  }

  "Test 7.2" in {
    output7.data.length should be (0)
  }

  "Test 7.3" in {
    output7.errorCode should be (2)
  }

  val answer8 = post("""{"data": [{"currencyFrom": "RUB","currencyTo": "USD","valueFrom": "STRING INSTEAD OF DOUBLE"}]}""".parseJson.prettyPrint)
  val output8 = answer8.body.parseJson.convertTo[OutcomingData[Answer]]

  "Test 8.1" in {
    answer8.code should be (200)
  }

  "Test 8.2" in {
    output8.data.length should be (0)
  }

  "Test 8.3" in {
    output8.errorCode should be (2)
  }

  val answer9 = post("""{"data}}stringSsBROKEN!""")
  val output9 = answer9.body.parseJson.convertTo[OutcomingData[Answer]]

  "Test 9.1" in {
    answer9.code should be (200)
  }

  "Test 9.2" in {
    output9.data.length should be (0)
  }

  "Test 9.3" in {
    output9.errorCode should be (3)
  }

}





