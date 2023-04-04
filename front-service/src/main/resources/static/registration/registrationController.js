app.controller("registrationController", function($rootScope, $scope, $http, $location, $window, $localStorage) {
//    const contextPath = 'http://localhost:8187/market-auth/api/v1';
    const contextPath = 'http://localhost:5555/auth/api/v1';

    $scope.$on('routeChangeStart', function(event, next, current) {
        if (typeof(current) != 'undefined') {
            $templateCache.remove(next.templateUrl);
        }
    });

//    if ($localStorage.springWebUser) {
//        $http.defaults.headers.common.Authorization;
//    };

    $scope.loginUser = function() {
    console.log('name=' + $scope.user.username + ', ' + 'email=' + $scope.user.email + ', ' + 'firstname=' + $scope.user.firstname + ', ' + 'lastname=' + $scope.user.lastname + ', ' + 'password=' + $scope.user.password + ', ' + 'confirmPassword=' + $scope.user.confirmPassword);//////////
        $http.post(contextPath + '/registration', $scope.user)
            .then(function successCallback(response) {
                if (response.data) {
                    $scope.user.username = null;
                    $scope.user.email = null;
                    $scope.user.firstname = null;
                    $scope.user.lastname = null;
                    $scope.user.password = null;
                    $scope.user.confirmPassword = null;
                    $location.path('/welcome');
                }
            }, function errorCallback(response) {
                $scope.error = response.data;
            });
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

    $rootScope.clearRole = function() {
        if($localStorage.currentRoles){
            var roles = null;
            $scope.roles = null;
            delete $localStorage.currentRoles;
        }
    };

    $scope.tryLogout = function() {
        if($rootScope.logout){
            $rootScope.logout();
        } else{
            $location.path('welcome')
        }
    };

    $rootScope.logout = function() {
        $scope.clearUser();
        $scope.clearRole();
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
        $location.path('welcome')
    };

    $rootScope.clearUser = function () {
        if($localStorage.springWebUser){
            delete $localStorage.springWebUser;
            $http.defaults.headers.common.Authorization = '';
        }
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.springWebUser) {
            return true;
        } else {
            return false;
        }
    };


    $scope.getCurrentUserRoles = function() {
        $http.get(contextPath + '/profile/roles')
            .then(function successCallback(response) {
                $localStorage.currentRoles = response.data.roles;
                $scope.setDefaultRole();
            }, function errorCallback(response) {
                alert('UNAUTHORIZED');
            });
    };

    $scope.setDefaultRole = function() {
        var roles = $localStorage.currentRoles;
        if (roles != null) {
            for (i=0; i<roles.length; i++){
                if (roles[i].name == 'ROLE_USER') {
                    $localStorage.defaultRole = roles[i];
                }
            }
        }
    }

});