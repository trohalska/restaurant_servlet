/**
 * check passwords match and prevents from submit if they are different
 */
let match = false;
function checkPass() {
    let password = $("#password").val();
    let confirmPassword = $("#confirmpassword").val();
    match = (password === confirmPassword);
}

$(document).ready(function () {
    $("#confirmpassword").keyup(checkPass);
});

function checkPasswordMatch() {
    if (match) {
        return true;
    } else {
        $("#confirmpassword").val('');
        alert('Passwords do not match!');
        return false;
    }
}

angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {

        $scope.signup = function (url) {
            if (!checkPasswordMatch()) {return;}
            let object = {
                'id': null,
                'login': document.querySelector('#login').value,
                'password': document.querySelector('#password').value,
                'email': document.querySelector('#email').value,
                'role': null,
                'time': null
            }
            console.log(object);
            $http({
                method: "POST",
                url: url,
                headers: {
                    "Content-Type": "application/json"
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                if (response.data.match(/Invalid/g)) {
                    let beginIndex = response.data.indexOf('Invalid');
                    let endIndex = response.data.indexOf('<', beginIndex);
                    let errorMsg = response.data.substring(beginIndex, endIndex);
                    document.querySelector('#errorMsg').innerHTML = errorMsg;
                    return;
                }
                location.replace('/restaurant/app/login');
            }, function (response) {
                alertErrors(response);
            });
        };

    }]);
