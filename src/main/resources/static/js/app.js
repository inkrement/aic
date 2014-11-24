'use strict';

var AngularSpringApp = {};
var App = angular.module('AngularSpringApp', ['AngularSpringApp.filters',
    'AngularSpringApp.services', 'AngularSpringApp.directives']);
// Declare app level module which depends on filters, and services
var sentimentCompany = null;
App.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/register', {
        templateUrl: 'templates/register.html',
        controller: SentimentController
    });
    $routeProvider.when('/sentiment', {
        templateUrl: 'templates/sentiment.html',
        controller: SentimentController
    });
    $routeProvider.otherwise({redirectTo: '/'});
}]);
