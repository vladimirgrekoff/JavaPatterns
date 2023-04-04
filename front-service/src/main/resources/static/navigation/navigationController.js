app.controller("navigationController", function($rootScope, $scope, $http, $location, $window, $localStorage) {
//    const contextPath = 'http://localhost:8189/market-core/api/v1';
    const contextPath = 'http://localhost:5555/core/api/v1';

    $scope.$on('routeChangeStart', function(event, next, current) {
        if (typeof(current) != 'undefined') {
            $templateCache.remove(next.templateUrl);
        }
    });

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization;
    };


    $scope.showProfilePage = function () {
        $location.path('profile');
    };

    $scope.showCartPage = function () {
        $location.path('cart');
    };

    $scope.showOrdersPage = function () {
        $location.path('orders');
    };

    $scope.showProductsPage = function () {
        $location.path('products');
    };

    $scope.showUsersPage = function () {
        $location.path('users');
    };


    $scope.tryLogout = function() {
        if($rootScope.logout){
            $rootScope.logout();
        } else {
            $location.path('welcome');
        }
    };


    $scope.showCurrentUserName = function(){
        return $localStorage.springWebUser.username;
    };

    $rootScope.hasRole = function(check) {
        var roles = $localStorage.currentRoles;
        if (roles != null) {
            for (i=0; i<roles.length; i++){
                if (check == roles[i].name) {
                    return true;
                }
            }
        }
        return false;
    };

//    $scope.readRoles();
//    $scope.showCurrentUserName();

});