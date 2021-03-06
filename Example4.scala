val userFile = sc.textFile("/yelp/user/user.csv")
val userReviewsFile = sc.textFile("/yelp/review/review.csv")
val userData = userFile.map(line => line.split("\\^")).map(res => (res(0) , res(1))).distinct.collectAsMap
val reviewData = userReviewsFile.map(line => line.split("\\^")).map(res => (res(1), 1))
val sumReviews = reviewData.groupByKey().map(res => {val r_sum = res._2.sum; (r_sum, res._1) })
val topNumReviews = sumReviews.sortBy(_._2).sortByKey(false,1)
val topReviewsFinal = topNumReviews.map(_.swap).take(10)
val uD = sc.broadcast(userData)
val finalJoin = topReviewsFinal.map(rD => (uD.value(rD._1),rD._2))
finalJoin.foreach(println)
