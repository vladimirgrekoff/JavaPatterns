app.controller("cartController", function($rootScope, $scope, $http, $location, $window, $localStorage, $templateCache) {
//    const contextPathCore = 'http://localhost:8189/market-core/api/v1';
//    const contextPathCart = 'http://localhost:8190/market-cart/api/v1';
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    const contextPathCart = 'http://localhost:5555/cart/api/v1/cart';



    $scope.$on('routeChangeStart', function(event, next, current) {
        if (typeof(current) !== 'undefined') {
            $templateCache.remove(next.templateUrl);
        }
    });

    if ($localStorage.springWebUser) {
        $http.defaults.headers.common.Authorization;
    };


    $scope.showNavigationPage = function () {
        $location.path('navigation');
    };

    $scope.showOrdersPage = function () {
        $location.path('orders');
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
            url: contextPathCore + '/products/pages',
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
        $http.get(contextPathCore + '/products/all')
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


    $scope.createOrder = function () {
        if($scope.checkCart()){
            $http.post(contextPathCore + '/orders')
                .then(function (response) {
                    $scope.loadCart();
                });
        }
    };

    $scope.checkCart = function(){
        if($scope.cart.totalPrice == null){
            alert("Для оформления заказа нужно выбрать не менее одного товара!");
            return false;
        }
        if($scope.cart.items.length == 0){
            alert("Для оформления заказа нужно выбрать не менее одного товара!");
            return false;
        }
        return true;
    };

    $scope.tryLogout = function() {
        if($rootScope.logout){
            $rootScope.logout();
        } else {
            $location.path('welcome')
        }
    };

    $scope.loadPageProducts();
    $scope.loadCart();

});