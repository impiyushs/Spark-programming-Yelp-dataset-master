val businessFile = sc.textFile("/yelp/business/business.csv")
val userReviewsFile = sc.textFile("/yelp/review/review.csv")
val businessData = businessFile.map(line => line.split("\\^")).map(res => (res(0) , res(1))).filter(_._2.contains("Stanford")).collectAsMap
val reviewData = userReviewsFile.map(line => line.split("\\^")).map(res => (res(2), res(1).concat(" Rating:".concat(res(3))))).collectAsMap
val rD = sc.broadcast(reviewData)
val res = businessData.map(bD => ("Business Id:".concat(bD._1),"User: ID".concat(rD.value(bD._1))))
res.foreach(println)
