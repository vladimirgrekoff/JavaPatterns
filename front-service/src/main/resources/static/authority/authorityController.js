app.controller('authorityController', function($rootScope, $scope, $http, $location, $templateCache, $window, $localStorage) {
//    const contextPath = 'http://localhost:8187/market-auth/api/v1/users';
    const contextPath = 'http://localhost:5555/auth/api/v1/users';

    $scope.$on('routeChangeStart', function(event, next, current) {
        if (typeof(current) != 'undefined') {
            $templateCache.remove(next.templateUrl);
        }
    });

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization;
    };

    $scope.newRoles = $localStorage.currentRoles;


    $scope.editUser;
    $scope.editUserCurrentRoles;
    $scope.deletedRoles;

    $scope.loadEditProfile = function () {
        let id = $rootScope.idEditUserProfile;
        $http.get(contextPath + '/' + id)
            .then(function (response) {
                $scope.editUser = response.data;
                $scope.setUserCurrentRoles();
            }, function errorCallback(response) {
                alert('Профиль не найден');
            });
    };

    $scope.setUserCurrentRoles = function(){
        $scope.editUserCurrentRoles = $scope.editUser.roles;
        $scope.deletedRoles = new Array();
    };



    $scope.deleteRole = function(deletedId){
        for(j=0; j < $scope.editUserCurrentRoles.length; j++){
            if($scope.editUserCurrentRoles[j].id == deletedId){
                $scope.deletedRoles.push($scope.editUserCurrentRoles[j]);
                $scope.editUserCurrentRoles.splice(j,1);
            }
        }
        if($scope.deletedRoles.length > 1){
            $scope.sortArray($scope.deletedRoles);
        }
    };

    $scope.restoreRole = function(){
        let id;
        let tempArray = $scope.editUserCurrentRoles;
        for (i=0; i < $scope.deletedRoles.length; i++) {
            if($scope.deletedRoles[i] != null || $scope.deletedRoles[i] != ''){
                if($scope.isCorrectRole($scope.deletedRoles[i], tempArray)) {
                    id = $scope.deletedRoles[i].id;
                    tempArray.push($scope.deletedRoles[i]);
                    $scope.deletedRoles.splice(i,1);
                }
            }
        }
        $scope.editUserCurrentRoles = tempArray;
        $scope.sortArray($scope.editUserCurrentRoles);
    };


    $scope.sortArray = function(sortedArray){
        let temp;
        for(i = 0; i < sortedArray.length; i++){
            for(j = i+1; j < sortedArray.length; j++){
                if(Number(sortedArray[i].id) > Number(sortedArray[j].id)){
                    temp = sortedArray[i];
                    sortedArray[i] = sortedArray[j];
                    sortedArray[j] = temp;
                    temp = null;
                }
            }
        }
    };


    $scope.addNewRole = function(){
        let role = $scope.getRoleById($scope.addedRoleId);
        if(role == $scope.selected || role == ''){
            alert("Роль должна иметь одно из значений в списке новых ролей!");
        }else if($scope.isCorrectRole(role, $scope.editUserCurrentRoles)) {
            $scope.editUserCurrentRoles.splice(role.id-1,0, role);
            $scope.addedRoleId = $scope.selected;
        } else {
            alert("В списке назначенных ролей пользователя повторы не допустимы!");
        }
    };

    $scope.isCorrectRole = function(checkedRole, editArray){
        for(k = 0; k < editArray.length; k++) {
            if(editArray[k].name == checkedRole.name){
                return false;
            }
        }
        return true;
    };

    $scope.getRoleById = function(id){
        for(i = 0; i < $scope.newRoles.length; i++) {
            if($scope.newRoles[i].id == id){
                return $scope.newRoles[i];
            }
        }
    }

    $scope.saveEditedProfile = function(){
        $scope.editUser.roles = $scope.editUserCurrentRoles;
        let editUser = $scope.editUser;
        $http.put(contextPath, editUser)
            .then(function (response) {
                editUser = null;
                $scope.loadEditProfile();
            });
    };


    $scope.printRoles = function(roles){
        for(i=0; i<roles.length; i++){
            console.log('id ' + roles[i].id + ', name ' + roles[i].name);///////////////////////////
        }
    }



    $scope.showUsersPage = function () {
            $location.path('users');
    };

    $scope.loadEditProfile();

});