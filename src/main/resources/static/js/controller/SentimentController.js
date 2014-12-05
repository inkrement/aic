'use strict';
/**
 * SentimentController
 * @constructor
 */
var SentimentController = function($scope, $http, $location) {

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

    $scope.getCompanySentiment = function(range) {
        $http.get("sentiment", {
            params: {
                name: sentimentCompany.name,
                //password: sentimentCompany.password,
                startDate: range.start,
                endDate: range.end
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
    };
};