<html>
<head>
<title>Web URL check</title>
<link rel="stylesheet"
	href="./bootstrap.min.css">
<script
	src="./angular.min.js"></script>
<script src="./form-validation.js"></script>
<script src="./form-validation-locale_pt-br.js"></script>
<script>
	angular
			.module('demo', [ 'ui.bootstrap.validation' ])
			.controller(
					'DemoCtrl',
					function($scope, $http) {
						$scope.setDefaultValues= function(){
							$scope.showElement = false;
							$scope.version = "";
							$scope.title = "";
							$scope.headings = ""
							$scope.internalLinks = "";
							$scope.externalLinks = "";
							$scope.loginFormAvailable = "";
						};
						
						$scope.save = function() {
							console.log('saved' + $scope.someModel.someField);
							$scope.setDefaultValues();
							$http
									.get(
											"./webapi/myresource?testUrl="
													+ $scope.someModel.someField)
									.success(
											function(response) {
												
												console.log('saved' + response);
												 if (response == "[object Object]") {
														$scope.version = response.version;
														$scope.title = response.title;
														$scope.headings = response.headings;
														$scope.internalLinks = response.internalLinks;
														$scope.externalLinks = response.externalLinks;
														$scope.loginFormAvailable = response.loginFormAvailable;
												} else {
													$scope.showElement = true;
													$scope.connectionProblem = response;
												}
											});
						};
					});
</script>
</head>
<body>

	<!--   <p><a href="webapi/myresource">Jersey resource</a>
    <p>Visit <a href="http://jersey.java.net">Project Jersey website</a>
    for more information on Jersey! -->
<body ng-app="demo">
	<div class="jumbotron">
		<h2 class="text-center">Web-Application for Analyzing Web-Sites!</h2>
	</div>
	<div class="container" ng-controller="DemoCtrl">
		<form name="someForm" novalidate ui-validation-submit="save()">
			<div class="form-group" ui-validation-show-errors>
				<label for="someFieldName" class="control-label">Enter URL</label> <input
					type="text" name="someFieldName" required class="form-control"
					ng-change="showElement=false" ng-model="someModel.someField"
					ng-minlength="3">
				<ui-validation-error-messages>
			</div>


			<button type="submit" class="btn btn-primary"
				ng-disabled="someForm.$pristine">Send</button>
		</form>
		<h4 class="text-info text-center">
			HTML version : <span class="label label-info">{{version}}</span>
		</h4>
		<h4 class="text-info text-center">
			Page title : <span class="label label-info">{{title}}</span>
		</h4>
		<h4 class="text-info text-center">
			Headings : <span class="label label-info">{{headings}}</span>
		</h4>
		<h4 class="text-info text-center">
			Internal links : <span class="label label-info">{{internalLinks}}</span>
		</h4>
		<h4 class="text-info text-center">
			External links : <span class="label label-info">{{externalLinks}}</span>
		</h4>
		
		<div class="alert alert-danger" ng-show="showElement">
			<strong>Error!</strong> There is an url problem.{{connectionProblem}}
		</div>

	</div>
</body>
</html>
