app.controller('profileController', function($rootScope, $scope, $http, $location, $templateCache, $window, $localStorage) {
//    const contextPath = 'http://localhost:8187/market-auth/api/v1/profile';
    const contextPath = 'http://localhost:5555/auth/api/v1/profile';

    $scope.$on('routeChangeStart', function(event, next, current) {
        if (typeof(current) != 'undefined') {
            $templateCache.remove(next.templateUrl);
        }
    });

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization;
    };


    $scope.loadProfile = function () {
        $http.get(contextPath + '/get')
            .then(function successCallback(response) {
                $scope.userProfile = response.data;
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    };

    $scope.showNavigationPage = function () {
            $location.path('navigation');
    };

    $scope.loadProfile();

});