package ontologies;

public interface RobotsVocabulary
{
   public static final String MESSAGE_INFO = "MessageInfo";
   public static final String MESSAGE_INFO_MAIN_SENDER_ID = "mainSenderId";
   public static final String MESSAGE_INFO_MAIN_RECEIVER_ID = "mainReceiverId";
   public static final String MESSAGE_INFO_SENDER_POS_X = "senderPosX";
   public static final String MESSAGE_INFO_SENDER_POS_Y = "senderPosY";
   public static final String MESSAGE_INFO_SENDER_RANGE = "senderRange";
   public static final String MESSAGE_INFO_DEADLINE = "deadline";
   public static final String MESSAGE_INFO_FACT = "f";
   public static final String MESSAGE_INFO_LOCATE_TASK = "lt";
   public static final String MESSAGE_INFO_CHECK_LOCATION_TASK = "clt";

   public static final String ENV_OBJECT = "EnvObject";
   public static final String ENV_OBJECT_ID = "id";
   public static final String ENV_OBJECT_POS_X = "posX";
   public static final String ENV_OBJECT_POS_Y = "posY";

   public static final String FACT = "Fact";
   public static final String FACT_TIME = "time";
   public static final String FACT_ID = "id";
   public static final String FACT_POS_X = "posX";
   public static final String FACT_POS_Y = "posY";

   public static final String TASK = "Task";
   public static final String TASK_EMPLOYER_ID = "employerId";
   public static final String TASK_PRIORITY = "priority";

   public static final String LOCATE_TASK = "LocateTask";
   public static final String LOCATE_TASK_EMPLOYER_ID = "employerId";
   public static final String LOCATE_TASK_PRIORITY = "priority";
   public static final String LOCATE_TASK_OBJECT_ID = "objectId";

   public static final String CHECK_LOCATION_TASK = "CheckLocationTask";
   public static final String CHECK_LOCATION_TASK_EMPLOYER_ID = "employerId";
   public static final String CHECK_LOCATION_TASK_PRIORITY = "priority";
   public static final String CHECK_LOCATION_TASK_POS_X = "posX";
   public static final String CHECK_LOCATION_TASK_POS_Y = "posY";
}
