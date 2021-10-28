

public aspect TraceAspectAlkindi {
   pointcut classToTrace(): within(ComponentApp) || within(DataApp) || within(ServiceApp); 

   pointcut methodToTrace(): classToTrace() && execution(String getName());



   before(): methodToTrace() {
      System.out.println("\t-->" + thisJoinPointStaticPart.getSignature() + //
            ", Line Number: " + thisJoinPointStaticPart.getSourceLocation().getLine());
      }
   

   after(): methodToTrace() {
      System.out.println("\t<--" + thisJoinPointStaticPart.getSourceLocation().getFileName());
   }

}
