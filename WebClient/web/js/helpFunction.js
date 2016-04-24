/**
 * Created by monkey_d_asce on 16-4-24.
 */

function msg(str, type)
{
    if (type == undefined) type = 0;
    alert(str);
}


function emptyCallBack()
{
    return;
}

/**
 * callback function demo for successful ajax
 * @param json : response information
 */
function defaultSuccess(json)
{
    msg("success : " + (typeof json == "string" ? json : JSON.stringify(json)));
}


/**
 * callback function demo for bad ajax
 * @param error : response information
 */
function defaultError(error)
{
    msg("error:" + error.responseText);
}


function ajax(url, type, data, goodCallBack, errorCallBack, isAsync)
{
    if (goodCallBack == undefined) goodCallBack = defaultSuccess;
    if (errorCallBack == undefined) errorCallBack = defaultError;
    if (isAsync == undefined) isAsync = true;

    $.ajax({
        type: type,
        url: url,
        //dataType: "json",
        data: data,
        async: isAsync,
        success: goodCallBack,  //set success callback function
        error: errorCallBack
    });
}


