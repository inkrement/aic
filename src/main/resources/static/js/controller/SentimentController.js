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

    $scope.saveCompany = function(newCompany) {
        $http.post('register', newCompany).success(function(){
            $scope.successTextAlert = newCompany.name + " successfully registered";
            $scope.showSuccessAlert = true;
        })
        .error(function(data, status, headers, config) {
            $scope.errorTextAlert = "Could not register company: " + status;
            $scope.showErrorAlert = true;
        });
    };
};