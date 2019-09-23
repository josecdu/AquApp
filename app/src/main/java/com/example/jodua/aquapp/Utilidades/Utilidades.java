package com.example.jodua.aquapp.Utilidades;

public class Utilidades {



    //CREAR TABLA MEDIDOR
    public static final String TABLA_MEDIDOR ="medidor";
    public static final String MEDIDOR_MED_COD ="med_cod";
    public static final String MEDIDOR_USU_COD ="usu_cod";
    public static final String MEDIDOR_MED_MARCA ="med_marca";
    public static final String MEDIDOR_MED_SERIE ="med_serie";
    public static final String MEDIDOR_MED_ESTADO ="med_estado";
    public static final String MEDIDOR_MED_ULTLEC ="med_ultlec";
    public static final String CREAR_TABLA_MEDIDOR=" CREATE TABLE IF NOT EXISTS `"+ TABLA_MEDIDOR +"` (\n" +
            "  `"+ MEDIDOR_MED_COD +"` integer NOT NULL,\n" +
            "  `"+ MEDIDOR_USU_COD +"` INT NOT NULL,\n" +
            "  `"+ MEDIDOR_MED_MARCA +"` VARCHAR(45) NULL,\n" +
            "  `"+ MEDIDOR_MED_SERIE +"` VARCHAR(45) NULL,\n" +
            "  `"+ MEDIDOR_MED_ESTADO +"` VARCHAR(45) NULL,\n" +
            "  `"+ MEDIDOR_MED_ULTLEC +"` INT NULL,\n" +
            "  PRIMARY KEY (`"+MEDIDOR_MED_COD+"` autoincrement))";
    //CREAR TABLA USUARIO
    public static final String TABLA_USUARIO= "usuario";
    public static final String USUARIO_USU_COD= "usu_cod";
    public static final String USUARIO_USU_NOM= "usu_nom";
    public static final String USUARIO_USU_APE= "usu_ape";
    public static final String USUARIO_USU_CI= "usu_ci";
    public static final String USUARIO_USU_DIR= "usu_dir";
    public static final String USUARIO_USU_CASANRO= "usu_casanro";
    public static final String USUARIO_USU_TEL= "usu_tel";
    public static final String USUARIO_USU_CORREO= "usu_correo";
    public static final String CREAR_TABLA_USUARIO= "CREATE TABLE IF NOT EXISTS `"+ TABLA_USUARIO +"` (\n" +
            "  `"+USUARIO_USU_COD+"` integer NOT NULL,\n" +
            "  `"+USUARIO_USU_NOM+"` VARCHAR(45) NULL,\n" +
            "  `"+USUARIO_USU_APE+"` VARCHAR(45) NULL,\n" +
            "  `"+USUARIO_USU_CI+"` VARCHAR(45) NULL,\n" +
            "  `"+USUARIO_USU_DIR+"` VARCHAR(150) NULL,\n" +
            "  `"+USUARIO_USU_CASANRO+"` VARCHAR(45) NULL,\n" +
            "  `"+USUARIO_USU_TEL+"` VARCHAR(45) NULL,\n" +
            "  `"+USUARIO_USU_CORREO+"` VARCHAR(45) NULL,\n" +
            "  PRIMARY KEY (`"+USUARIO_USU_COD+"` autoincrement));";
//CREAR TABLA PERSONAL
    public static final String TABLA_PERSONAL= "personal";
    public static final String PERSONAL_PER_COD= "per_cod";
    public static final String PERSONAL_PER_NOM= "per_nom";
    public static final String PERSONAL_PER_APE= "per_ape";
    public static final String PERSONAL_PER_CI= "per_ci";
    public static final String PERSONAL_PER_DIR= "per_dir";
    public static final String PERSONAL_PER_LOG= "per_log";
    public static final String PERSONAL_PER_PASS= "per_pass";
    public static final String PERSONAL_PER_ESTADO= "per_estado";
    public static final String CREAR_TABLA_PERSONAL=  "CREATE TABLE IF NOT EXISTS `"+TABLA_PERSONAL+"` (\n" +
        "  `"+PERSONAL_PER_COD+"` integer NOT NULL,\n" +
        "  `"+PERSONAL_PER_NOM+"` VARCHAR(45) NULL,\n" +
        "  `"+PERSONAL_PER_APE+"` VARCHAR(45) NULL,\n" +
        "  `"+PERSONAL_PER_CI+"` VARCHAR(45) NULL,\n" +
        "  `"+PERSONAL_PER_DIR+"` VARCHAR(45) NULL,\n" +
        "  `"+PERSONAL_PER_LOG+"` VARCHAR(45) NULL,\n" +
        "  `"+PERSONAL_PER_PASS+"` VARCHAR(45) NULL,\n" +
        "  `"+PERSONAL_PER_ESTADO+"` VARCHAR(45) NULL,\n" +
        "  PRIMARY KEY (`"+PERSONAL_PER_COD+"` autoincrement));";
    //CREAR TABLA LECTURA
    public static final String TABLA_LECTURA= "lectura";
    public static final String LECTURA_LEC_COD= "lec_cod";
    public static final String LECTURA_FLEC_ACT= "flec_act";
    public static final String LECTURA_LEC_ACT= "lec_act";
    public static final String LECTURA_FLEC_ANT= "flec_ant";
    public static final String LECTURA_LEC_ANT= "lec_ant";
    public static final String LECTURA_LEC_ESTADO= "lec_estado";
    public static final String LECTURA_LEC_FOTO= "lec_foto";
    public static final String LECTURA_PER_COD= "per_cod";
    public static final String LECTURA_MED_COD= "med_cod";
    public static final String LECTURA_CIC_COD= "cic_cod";
    public static final String CREAR_TABLA_LECTURA=  "CREATE TABLE IF NOT EXISTS `"+TABLA_LECTURA+"` (\n" +
            "  `"+LECTURA_LEC_COD+"` integer NOT NULL,\n" +
            "  `"+LECTURA_FLEC_ACT+"` DATE NULL,\n" +
            "  `"+LECTURA_LEC_ACT+"` INT NULL,\n" +
            "  `"+LECTURA_FLEC_ANT+"` DATE NULL,\n" +
            "  `"+LECTURA_LEC_ANT+"` INT NULL,\n" +
            "  `"+LECTURA_LEC_ESTADO+"` VARCHAR(45) NULL,\n" +
            "  `"+LECTURA_LEC_FOTO+"` VARCHAR(150) NULL,\n" +
            "  `"+LECTURA_PER_COD+"` INT NOT NULL,\n" +
            "  `"+LECTURA_MED_COD+"` INT NOT NULL,\n" +
            "  `"+LECTURA_CIC_COD+"` INT NOT NULL,\n" +
            "  PRIMARY KEY (`"+LECTURA_LEC_COD+"` autoincrement),\n" +
            " \n" +
            "  CONSTRAINT `fk_lectura_Personal1`\n" +
            "    FOREIGN KEY (`per_cod`)\n" +
            "    REFERENCES `Personal` (`per_cod`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION,\n" +
            "  CONSTRAINT `fk_lectura_medidor1`\n" +
            "    FOREIGN KEY (`med_cod`)\n" +
            "    REFERENCES `medidor` (`med_cod`)\n" +
            "    ON DELETE NO ACTION\n" +
            "    ON UPDATE NO ACTION);";

}
