'use strict';
/**
 * RegisterController
 * @constructor
 */
var RegisterController = function($scope, $http) {
    $scope.save = function(newCompany) {
        $http.post('register', newCompany).success(function(){
            $scope.successTextAlert = newCompany.name + " successfully registered";
            $scope.showSuccessAlert = true;
        });
    };
};
