angular.module('hello', ['ngRoute']).config(function ($routeProvider, $httpProvider) {

    $routeProvider.when('/', {
        templateUrl: 'home.html',
        controller: 'home',
        controllerAs: 'controller'
    }).when('/login', {
        templateUrl: 'login.html',
        controller: 'navigation',
        controllerAs: 'controller'
    }).when('/users', {
        templateUrl: 'usersList.html',
        controller: 'navigation',
        controllerAs: 'controller'
    }).otherwise('/');

    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';

}).controller('navigation',

    function ($rootScope, $http, $location, $route) {

        var self = this;

        self.tab = function (route) {
            return $route.current && route === $route.current.controller;
        };

        var authenticate = function (credentials, callback) {

            var headers = credentials ? {
                    authorization: "Basic "
                    + btoa(credentials.username + ":"
                        + credentials.password)
                } : {};

            $http.get('/users/user', {
                headers: headers
            }).then(function (response) {
                if (response.data.name) {
                    $rootScope.userLogin = response.data.name;
                    $rootScope.authenticated = true;
                    $rootScope.isAdmin = response.data.authorities[0].authority === 'ROLE_ADMIN';
                    $rootScope.isMember = response.data.authorities[0].authority === 'ROLE_MEMBER';
                } else {
                    $rootScope.authenticated = false;
                }
                callback && callback($rootScope.authenticated);
            }, function () {
                $rootScope.authenticated = false;
                callback && callback(false);
            });

        };

        authenticate();

        self.credentials = {};
        self.login = function () {
            authenticate(self.credentials, function (authenticated) {
                if (authenticated) {
                    $location.path("/");
                    self.error = false;
                    $rootScope.authenticated = true;
                } else {
                    $location.path("/login");
                    self.error = true;
                    $rootScope.authenticated = false;
                }
            })
        };

        self.logout = function () {
            $http.post('logout', {}).finally(function () {
                $rootScope.authenticated = false;
                $location.path("/");
            });
        }

    }).controller('home', function ($http, $rootScope) {

    var self = this;

    self.loadBooks = function () {
        $http.get('/books', {})
            .then(function (response) {
                self.books = response.data;
            }, function () {

            });
    };

    self.loadBooks();

    self.sortType = 'title';
    self.sortReverse = false;
    self.searchFilter = '';

    self.addBook = function () {

        self.newBook.status = 0;
        $http.post('/books', self.newBook).finally(function () {

            self.loadBooks();
        });
    };

    self.deleteBook = function (bookId) {

        $http.delete('/books/' + bookId, {}).finally(function () {

            self.loadBooks();
        });
    };

    self.borrowBook = function (bookId, status) {

        $http.put('/books/' + bookId + "/" + status, {"userLogin": $rootScope.userLogin}).finally(function () {

            self.loadBooks();
        });
    };
}).controller('users', function ($http) {

    var self = this;

    self.loadUsers = function () {
        $http.get('/users', {})
            .then(function (response) {
                self.users = response.data;
            }, function () {

            });
    };

    self.loadUsers();

    self.sortType = 'name';
    self.sortReverse = false;
    self.searchFilter = '';

    self.addUser = function () {

        self.newUser.enabled = true;
        self.newUser.role_id = 3;
        $http.post('/users', self.newUser).finally(function () {

            self.loadUsers();
        });
    };

    self.deleteUser = function (userId) {

        $http.delete('/users/' + userId, {}).finally(function () {

            self.loadUsers();
        });
    };
});
