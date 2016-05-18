package aspectj;


import org.aspectj.lang.JoinPoint;

import java.util.logging.Logger;

/**
 * Created by monkey_d_asce on 16-5-18.
 */
public aspect AutoLog
{


    private static final Logger logger = Logger.getLogger(AutoLog.class.getName());

    /*
     intellij 配置教程 https://www.jetbrains.com/help/idea/2016.1/using-the-aspectj-compiler-ajc.html
     pointcut 语法见http://blog.csdn.net/zl3450341/article/details/7673960
    */
    pointcut businessCall(): execution(* business..*(..) );



    before(): businessCall()
            {
                logger.info("enter: " + getClassName(thisJoinPoint) + getParaInfo(thisJoinPoint));
            }

    after() returning(Object o): businessCall()
            {

                logger.info("return: " + getClassName(thisJoinPoint) + ": " + (o == null ? "void" : o.toString()));

            }


    private String getClassName(JoinPoint joinPoint)
    {
        return joinPoint.getSignature().getDeclaringType().getName()
                + '.' + joinPoint.getSignature().getName();
    }

    private String getParaInfo(JoinPoint joinPoint)
    {
        String logStr = "(";
        boolean isFirst = true;
        for (Object arg : joinPoint.getArgs())
        {
            logStr += arg.toString();
            if (!isFirst)
            {
                isFirst = false;
                logStr += ',';
            }
        }
        logStr += ')';

        return logStr;
    }


}
