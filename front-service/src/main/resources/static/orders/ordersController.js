angular.module('market').controller('ordersController', function ($rootScope, $scope, $http, $location, $window, $localStorage, $templateCache) {


    $scope.$on('routeChangeStart', function(event, next, current) {
        if (typeof(current) !== 'undefined') {
            $templateCache.remove(next.templateUrl);
        }
    });

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization;
    };

    $scope.showCartPage = function () {
        $location.path('cart');
    };

    $scope.showNavigationPage = function () {
        $location.path('navigation');
    };

    $scope.loadOrders = function () {
        $http.get('http://localhost:5555/core/api/v1/orders')
            .then(function (response) {
                $scope.orders = response.data;
            });
    };

    $scope.tryLogout = function() {
        if($rootScope.logout){
            $rootScope.logout();
        } else {
            $location.path('welcome')
        }
    };

    $scope.loadOrders();
});