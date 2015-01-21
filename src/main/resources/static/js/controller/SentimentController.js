'use strict';
/**
 * SentimentController
 * @constructor
 */
var SentimentController = function($scope, $http, $location) {

    $scope.range = { start: "2015-01-01", end: "2015-01-25" };

    /* Helper function for current active menu in view */
    $scope.isActive = function (viewLocation) {
        var active = (viewLocation === $location.path());
        return active;
    };

    /* Helper function for showing sentiment link */
    $scope.loggedIn = function() {
        if (sentimentCompany)
            return true;

        return false;
    }

    $scope.getCompanySentiment = function(range, classif) {
        console.log(classif);
        $http.get("sentiment", {
            params: {
                name: sentimentCompany.name,
                startDate: range.start,
                endDate: range.end,
                classifier: classif
            }
        }).success(function(data) {
            $scope.sentiment = data;
            $scope.showSentiment = true;
        }).error(function(data, status, headers, config){
            $scope.errorTextAlert = "Could not fetch tweets: " + data.message;
            $scope.showErrorAlert = true;
            $scope.showSentiment = false;
        });
    }

    $scope.saveCompany = function(newCompany) {
        $http.post('register', newCompany).success(function(){
            $scope.successTextAlert = newCompany.name + " registered successfully";
            $scope.showSuccessAlert = true;
            sentimentCompany = newCompany;
        })
        .error(function(data, status, headers, config) {
            $scope.errorTextAlert = "Could not register company: " + status;
            $scope.showErrorAlert = true;
        });

        $http.get("classifier").success(function(data) {
            var _cl = [];
            angular.forEach(data, function(value, key) {
                _cl.push(value);
            });
            classifiers = _cl;
        });
    };

    $scope.classifiers = classifiers;
};