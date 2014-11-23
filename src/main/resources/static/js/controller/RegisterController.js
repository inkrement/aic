'use strict';
/**
 * RegisterController
 * @constructor
 */
var RegisterController = function($scope, $http) {
    $scope.saveCompany = function(newCompany) {
        $http.post('register').success(function(carList){

        });
    };
};