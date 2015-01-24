colours <- c("white", "lightgray")
data <- structure(list(KNN=c(61.0028, 55.7103), C_SVC=c(49.3036, 49.3036), NU_SVC=c(71.8663, 57.9387), 
                       SMO=c(68.8022, 59.0529), NAIVE_BAYES=c(66.5738, 49.3036), BAYES_NET=c(66.5738, 49.3036)), 
                  .Names = c("KNN", "C_SVC", "NU_SVC", "SMO", "NAIVE BAYES", "BAYES NET"), class="data.frame", 
                  row.names = c(NA, -2L))
#attach(data)
#print(data)

midpoints <- barplot(as.matrix(data), ylab="% Correctly Classified", beside=TRUE, 
                     cex.main=0.5, cex.names=0.7, col=colours, ylim=c(0,100), xpd=FALSE)
text(midpoints, 3, labels=round(as.matrix(data), 1), cex=0.7)
legend("topleft", c("Trainingset with 1118 entries", "Trainingset with 100 entries"), bty="n", fill=colours)

#evaluation <- table(svm_c_svc, svm_nu_svc, smo, naive_bayes, bayes_network, knn)
#plot(evaluation)
#barplot(evaluation)