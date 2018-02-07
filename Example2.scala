val userFile = sc.textFile("/yelp/user/user.csv")
val userReviewsFile = sc.textFile("/yelp/review/review.csv")
val userData = userFile.map(line => line.split("\\^")).map(res => (res(0) , res(1))).distinct.collectAsMap
val reviewData = userReviewsFile.map(line => line.split("\\^")).map(res => (res(1),res(3).toDouble))
val avgReviews = reviewData.groupByKey().map(res => {val avg = res._2.sum / res._2.size; (avg, res._1) })
val finalReviews = avgReviews.map(_.swap)
val uD = sc.broadcast(userData)
val finalJoin = finalReviews.map(rD => (uD.value(rD._1),rD._2 ))
val result = finalJoin.collectAsMap
val userToFind = readLine("Enter the user to find the avg reviews:\n")
result.get(userToFind)
