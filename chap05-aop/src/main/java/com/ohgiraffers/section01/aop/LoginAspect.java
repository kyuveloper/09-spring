package com.ohgiraffers.section01.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

// @Aspect : pointcut과 advice를 하나의 클래스 단위로 정의하기 위한 어노테이션이다.
/*
* pointcut : 특정 조건에 의해 필터링된 조인포인트, 수많은 조인포인트 중에 특정 메소드만 횡단 공통기능을 위해서 사용한다.
* advice : 횡단 관심에 해당하는 공통기능의 코드, 독립된 클래스의 메소드로 작성한다.
*   - Before : 메소드 실행 전에 동작
*   - After : 메소드 실행 후에 동작
*   - After-returning : 메소드가 정상적으로 실행된 후에 동작
*   - After-throwing : 예외가 발생한 후에 동작
*   - Around : 메소드 호출 이전, 이후, 예외발생 등 모든 시점에 동작
* */
@Aspect
@Component
public class LoginAspect {

    /*
    * pointcut : 관심 조인 포인트를 결정하여 어드바이스가 실행되는 시기를 제어한다.
    * execution : 메소드 실행 조인 포인트를 매칭한다.*/

    // execution([수식어] 리턴타입 [클래스이름].이름(파라미터))
    // 수식어 생략 가능 public private protected default (접근제어자)
    // *Service.*(..) : 매개변수가 0개 이상인 모든 메서드
    // *Service.*(*) : 매개변수가 1개인 모든 메서드
    // *Service.*(*,..) : 매개변수가 2개인 모든 메서드
    @Pointcut("execution(* com.ohgiraffers.section01.aop.*Service.*(..))")
    public void logPointcut() {

    }
    /*
    * joinPoint : 포인트컷으로 패치한 실행 지점
    * 이렇게 매치된 조인포인트에서 해야할 일이 advice이다.
    * 매개변수로 전달한 joinPoint 객체는 현재 조인 포인트의 메소드명, 인수값 등의 자세한 정보를 엑세스 할 수 있다.
    * */
    @Before("LoginAspect.logPointcut()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("before joinPoint.getTarget() : " + joinPoint.getTarget());
        System.out.println("before joinPoint.getSignature() : " + joinPoint.getSignature());
        if (joinPoint.getArgs().length > 0) { // 매개변수가 있을 때
            System.out.println("before joinPoint.getArgs()[0] : " + joinPoint.getArgs()[0]);
        }
    }

    @After("logPointcut()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("after joinPoint.getTarget() : " + joinPoint.getTarget());
        System.out.println("after joinPoint.getSignature() : " + joinPoint.getSignature());
        if (joinPoint.getArgs().length > 0) { // 매개변수가 있을 때
            System.out.println("after joinPoint.getArgs()[0] : " + joinPoint.getArgs()[0]);
        }
    }

    /*
    * returning 속성은 리턴갑으로 받아올 오브젝트의 매개변수 이름과 동일해야 한다.
    * 또한 jointPoint는 반드시 첫 번째 매개변수로 선언해야 한다.
    * */
    /*
    * aop가 적용될 메서드가 에러없이 성공적으로 실행된 이후의 시점을 말한다.*/
    @AfterReturning(pointcut = "logPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("after returning result " + result);
        if (result != null && result instanceof Map) {
            ((Map<Long, MemberDTO>) result).put(100L, new MemberDTO(100L, "반환 값 가공"));
        }
    }
    /*
    * aop가 적용될 메서드가 에러를 발생하여 Exception을 던지는 시점을 말한다. */
    // throwing 속성의 이름과 매개변수의 이름이 동일해야 한다.
    @AfterThrowing(pointcut = "logPointcut()", throwing = "exception")
    public void logAfterThrowing(Throwable exception) {
        System.out.println("after throwing exception " + exception);
    }

    /*
    * Around Advice는 가장 강력한 어드바이스이다.
    * 이 어드바이스는 조인포인트를 완전히 장악하기 때문에
    * 앞에서 살펴 본 어드바이스 모두 Around 어드바이스로 조합할 수 있다.
    * 심지어 원본 조인포인트를 언제 실행할지, 실행 자체를 안할지, 계속 실행할지 여부까지도 제어한다.
    * AroundAdvice의 조인포인트 매개변수는 ProceedingJoinPoint로 고정되어 있다.
    * JoinPoint의 하위 인터체이스로 원본 조인포인트의 진행 시점을 제어할 수 있다.
    * 조인포인트 진행하는 호출을 잊는 경우가 자주 발생하기 때문에 주의해야 하며
    * 최소한의 요건을 충족하면서도 가장 기능이 약한 어드바이스를 쓰는게 바람직하다.
    * */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Around Before " + joinPoint.getSignature().getName());

        // 원본 조인 포인트를 실행한다.
        Object result = joinPoint.proceed();
        System.out.println("Around After " + joinPoint.getSignature().getName());

        // 원본 조인포인트를 호출한 쪽 혹은 다른 어드바이스가 다시 실행할 수 있도록 반환한다.
        return result;
    }
}
