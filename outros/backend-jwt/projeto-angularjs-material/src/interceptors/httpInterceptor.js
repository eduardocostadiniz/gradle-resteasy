angular.module("meuProjeto").factory("HttpInterceptor", function($q, $location, $rootScope) {
  return {
    request: function(request) {
      console.log("URL = " + request.url);

      var tokenUsr = window.localStorage.getItem("tokenUsr");

      var loginRequisicao = request.url.indexOf("login") >= 0;
      console.log("Eh requisicao de login: = " + loginRequisicao);

      if (!loginRequisicao) {

        if (tokenUsr) {
          console.log("Token Existe, prosseguir");
          console.log("TOKEN =");
          console.log(tokenUsr);
          //se possuir token, manda na requisicao
          request.headers.Authorization = "Bearer " + tokenUsr;
        } else {
          console.log("Não é login e o TOKEN não existe!!! Rejeitar a requisição e redirecionar para o login");
          $q.reject(request);
          $location.path("/login");
        }

      } else {
        if (tokenUsr) {
          console.log("Eh requisicao de login e EXISTE UM TOKEN JA");
          console.log(tokenUsr);
          //se possuir token, manda na requisicao
          request.headers.Authorization = "Bearer " + tokenUsr;
          $location.path("/");
        } else {
          console.log("Eh login, deixa passar a requisição ");
        }
      }

      console.log();
      console.log("____________________________________");
      console.log();
      return request;
    },
   requestError: function(rejection) {
     console.log("DEU ZEBRA EM ALGUMA COISA ... REQUEST ");

      if (canRecover(rejection)) {
        return responseOrNewPromise
      }
      return $q.reject(rejection);
    },
    response: function(response) {
      return response;
    },
   responseError: function(rejection) {
     console.log("DEU ZEBRA EM ALGUMA COISA ... RESPONSE");

      // do something on error
      if (canRecover(rejection)) {
        return responseOrNewPromise
      }
      return $q.reject(rejection);
    }
  };



});
