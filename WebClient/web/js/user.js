/**
 * Created by monkey_d_asce on 16-4-24.
 */

$.fn.serializeObject = function ()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function ()
    {
        if (o[this.name])
        {
            if (!o[this.name].push)
            {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else
        {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

/**
 * 登陆并且认证
 * @warning: 方法名起成login的话可能就没法调用了
 */
function userLogin()
{
    var loginForm = $("#loginForm");

    var formData = loginForm.serializeObject();

    formData.action = "login";

    ajax("User", "post", formData,
        function (principal)  // success
        {
            switch (principal)
            {
                case "admin":
                    self.location = "manage.jsp";
                    setCookie("user", {name: formData.username, principal: principal}, "d1", "/");
                    break;
                case "user":
                    self.location = "shop.jsp";
                    setCookie("user", {name: formData.username, principal: principal}, "d1", "/");
                    break;
                default:
                    msg("invalid username or password");
            }


        }, function ()
        {
            msg("invalid username or password");
        });

}


function SignUp()
{
    var regForm = $("#SignUpForm");
    var formData = regForm.serializeObject();
    formData.action = "register";

    ajax("User", "post", formData,
        function (data, status)
        {
            msg("SignUp success!", 1);
            self.location.reload();
        }, function (error)
        {
            alert(error.responseText);
        }
    );
}


function logout()
{
    setCookie("user", "{}", "d1", "/");
    ajax("User", "post",
        {
            action: "logout"
        }, function ()
        {
            self.location = "index.jsp";
        });
}

function chPwd()
{
    var regForm = $("#pwdForm");
    var formData = regForm.serializeObject();
    formData.action = "chpwd";
    ajax("User", "post", formData,
        function ()
        {
            msg("success!", 1);
            self.location.reload();
        });
}
