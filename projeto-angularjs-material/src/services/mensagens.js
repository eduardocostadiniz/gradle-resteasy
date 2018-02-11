angular.module("meuProjeto").factory("mensagens", function($mdDialog, $mdToast) {

  return {
    mostrarToast: mostrarToast
  }

  function mostrarToast(mensagem) {
    $mdToast.show($mdToast.simple()
        .textContent(mensagem)
        .position("bottom right")
        .hideDelay(2000))
  }

  // $scope.mostrarModal = function(mensagem, titulo, evento) {
  //   $mdDialog.show(
  //     $mdDialog.alert()
  //       .parent(angular.element(document.querySelector('#popupContainer')))
  //       .clickOutsideToClose(true)
  //       .title(titulo)
  //       .textContent(mensagem)
  //       .ariaLabel('Alert Dialog Demo')
  //       .ok('OK')
  //       .targetEvent(evento)
  //   );
  // }

});
