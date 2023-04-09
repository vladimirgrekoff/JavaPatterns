app.controller("productController", function($rootScope, $scope, $http, $location, $localStorage, $templateCache) {
//    const contextPath = 'http://localhost:8189/market-core/api/v1';
    const contextPath = 'http://localhost:5555/core/api/v1';




    $scope.$on('routeChangeStart', function(event, next, current) {
        if (typeof(current) != 'undefined') {
            $templateCache.remove(next.templateUrl);
        }
    });



    $scope.defaultNumber = 5;
    $scope.currentPage;

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

    $scope.currentPageCheck = function(offset){
        console.log('текущая ' + $scope.currentPage);//////////////////////////////////
        $scope.currentPage = $scope.currentPage + offset;
    return $scope.currentPage;
    };

    $scope.loadPageProducts = function (offset, first, last) {
        limit = $scope.checkLimit();
        let page;
        page = $scope.currentPageCheck(offset);
        $http({
            url: contextPath + '/admin/products/pages',
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
                $scope.ProductsList = response.data.content;
                console.log('текущая пришла ' + response.data.content);//////////////////////////////////
        });
    };

    $scope.loadAllProducts = function () {
        $http.get(contextPath + '/admin/products/all')
            .then(function (response) {
                $scope.ProductsList = response.data;
            });
    };

    $scope.updateProduct = function (product, delta) {
        product.price = product.price + delta;
        $http.put(contextPath + '/admin/products', product)
            .then(function (response) {
                product = null;
                $scope.loadPageProducts();
            });
    };



    $scope.addNewProduct = function () {
        if ($scope.isEmptyProductData() == false){
            $scope.newProduct.categoryTitle = $scope.categoryTitle;
            $http.post(contextPath + '/admin/products', $scope.newProduct)
                .then(function (response) {
                    $scope.newProduct.title = null;
                    $scope.newProduct.price = null;
                    $scope.newProduct.categoryTitle = null;
                    $scope.categoryTitle = $scope.selected;
                    $scope.loadPageProducts(0, false, true);
                });
        }
    };


    $scope.isEmptyProductData = function(){
        if($scope.newProduct == undefined || $scope.newProduct == null){
            alert("При добавлении нового продукта все поля должны быть заполнены!");
            return true;
        }
        if($scope.newProduct.title == undefined || $scope.newProduct.title == null || $scope.newProduct.title == ''){
            alert("Поле 'Наименование' должно быть заполнено!");
            return true;
        }
        if($scope.newProduct.price == undefined || $scope.newProduct.price == null || $scope.newProduct.price == ''){
            alert("Поле 'Цена' должно быть заполнено!");
            return true;
        }
        if($scope.categoryTitle == $scope.selected || $scope.categoryTitle == ''){
            alert("Категория должна иметь одно из значений в списке 'Выбор категории'!");
            return true;
        }
        return false;
    };

    $scope.deleteProduct = function (productId) {
        $http.delete(contextPath + '/admin/products/' + productId)
            .then(function (response) {
                $scope.loadPageProducts();
            });
    };

    $scope.showNavigationPage = function () {
        $location.path('navigation');
    };

    $scope.tryLogout = function() {
        if($rootScope.logout){
            $rootScope.logout();
        } else {
            $location.path('welcome')
        }
    };

//    $scope.loadAllProducts();
    $scope.loadPageProducts();

});