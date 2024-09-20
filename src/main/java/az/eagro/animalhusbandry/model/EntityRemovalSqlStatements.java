package az.eagro.animalhusbandry.model;

public class EntityRemovalSqlStatements {

    public static final String FINAL_MONITORING_JUDGEMENT_REMOVAL_SQL_STATEMENT =
            "UPDATE AnimalHusbandry.FinalMonitoringJudgement SET DeletedAt = GETDATE() WHERE FinalMonitoringJudgementId = ?";

}
