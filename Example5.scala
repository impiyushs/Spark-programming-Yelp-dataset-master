val businessFile = sc.textFile("/yelp/business/business.csv")
val userReviewsFile = sc.textFile("/yelp/review/review.csv")
val businessData = businessFile.map(line => line.split("\\^")).map(res => (res(0) , res(1))).filter(_._2.contains("TX ")).collectAsMap
val reviewData = userReviewsFile.map(line => line.split("\\^")).map(res => (res(2), 1))
val sumReviews = reviewData.groupByKey().map(res => {val r_sum = res._2.sum; (res._1, r_sum) }).collectAsMap
val rD = sc.broadcast(topReviewsFinal)
val finalJoin = topReviewsFinal.map(bD => ("Business ID: ".concat(bD._1),rD.value(bD._1)))
import collection.immutable.ListMap     
ListMap(finalJoin.toList.sortBy{_._2}:_*).foreach(println)
