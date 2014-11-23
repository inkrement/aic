'use strict';

var AngularSpringApp = {};
var App = angular.module('AngularSpringApp', ['AngularSpringApp.filters',
    'AngularSpringApp.services', 'AngularSpringApp.directives']);
// Declare app level module which depends on filters, and services
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/register', {
        templateUrl: 'templates/register.html',
        controller: RegisterController
    });
    $routeProvider.otherwise({redirectTo: '/'});
}]);
