app.controller("welcomeController", function($rootScope, $scope, $http, $location, $localStorage, $templateCache) {
//    const contextPath = 'http://localhost:8189/market-core/api/v1';
    const contextPath = 'http://localhost:5555/core/api/v1';
    const contextPathCart = 'http://localhost:5555/cart/api/v1/cart';

    $scope.$on('routeChangeStart', function(event, next, current) {
        if (typeof(current) != 'undefined') {
            $templateCache.remove(next.templateUrl);
        }
    });

    $rootScope.run = function() {
        if ($localStorage.springWebUser) {
            try {
                let jwt = $localStorage.springWebUser.token;
                let payload = JSON.parse(atob(jwt.split('.')[1]));
                let currentTime = parseInt(new Date().getTime() / 1000);
                if (currentTime > payload.exp) {
                    console.log("Token is expired!!!");////////////////////////////////////////////
                    delete $localStorage.springWebUser;
                    $http.defaults.headers.common.Authorization = '';
                }
            } catch (e) {
            }

            if ($localStorage.springWebUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.springWebUser.token;
            }
        }
        if (!$localStorage.marketGuestCartId) {
            $http.get('http://localhost:5555/cart/api/v1/cart/generate_id')
                .then(function (response) {
                    $localStorage.marketGuestCartId = response.data.value;
                });
        }
    };

    $scope.defaultNumber = 5;
    $scope.currentPage;
    $scope.pagesCount;

    if($localStorage.productsOnPage){
        $scope.applyValue = $localStorage.productsOnPage;
    }else{
        $scope.applyValue = $scope.defaultNumber;
    };

    $scope.applyValue;

    $scope.applyNumberProducts = function(){
        $localStorage.productsOnPage = $scope.numberProduct;
        $scope.loadPageProducts(0, true, false);
    };

    $scope.checkLimit = function(){
//        if(limit == 5){
        if($localStorage.productsOnPage){
            if($localStorage.productsOnPage != undefined && $localStorage.productsOnPage != null) {
                limit = $localStorage.productsOnPage;
                $scope.defaultNumber = limit;
            }
        } else{
            limit = $scope.defaultNumber;
        }
        return limit;
    };


    $scope.showLoginPage = function () {
        $location.path('login');
    };


    $scope.showRegistrationPage = function () {
        $location.path('registration');
    };

    $scope.calculateCurrentPage = function(offset){
        let page;
        page = $scope.currentPage;
        if (page == undefined || page == null){
            page = 0;
        }
        page = page + offset;
        if(page < 0){
            page = 0;
        }
        if($scope.pagesCount != undefined){
            if(page >= $scope.pagesCount - 1){
                page = $scope.pagesCount - 1;
            }
        }
        $scope.currentPage = page;
        return $scope.currentPage;
    };

    $scope.loadPageProducts = function (offset, first, last) {
        if (offset == undefined){
            offset = 0;
        }
        limit = $scope.checkLimit();
        let page;
        page = $scope.calculateCurrentPage(offset);

        $http({
            url: contextPath + '/products/pages',
            method: 'GET',
            params: {
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                part_title: $scope.filter ? $scope.filter.part_title : null,
                offset: offset,
                limit: limit,
                first: first,
                last: last,
                current_page: page
            }
        }).then(function (response) {
                $scope.currentPage = response.data.page;
                $scope.pagesCount = response.data.totalPage;
                $scope.ProductsList = response.data.items;
        });
    };

    $scope.loadAllProducts = function () {
        $http.get(contextPath + '/products/all')
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };

    $scope.loadCart = function () {
//            $http.get(contextPathCart + '/cart')
            $http.get(contextPathCart + '/' + $localStorage.marketGuestCartId)
                .then(function (response) {
                $scope.cart = response.data;
        });
    };


    $scope.addProductToCart = function (productId) {
        $http.get(contextPathCart + '/' + $localStorage.marketGuestCartId + '/add/' + productId)
            .then(function (response) {
                productId = null;
                $scope.loadCart();
            });
    };

    $scope.deleteProductFromCart = function (productId) {
        $http.delete(contextPathCart + '/' + $localStorage.marketGuestCartId + '/delete/' + productId)
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.clearCart = function () {
        $http.delete(contextPathCart + '/' + $localStorage.marketGuestCartId + '/clear')
            .then(function (response) {
                $scope.loadCart();
            });
    };

    $scope.guestCreateOrder = function () {
        alert('Для оформления заказа необходимо войти в учетную запись');
    }


    $scope.tryLogout = function() {
        if($rootScope.logout){
            $rootScope.logout();
        } else {
            $location.path('welcome')
        }
    };


    $rootScope.run();
    $scope.loadPageProducts();

});