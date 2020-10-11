package com.gstu.pda;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.EnumSet;

/**
 * createdAt: 10/10/2020
 * project: One
 *
 * @author Alex
 */
public class DatabaseInitializerRunner {

    private static final String SCRIPT_FILE = "exportScript.sql";
    private static final String CONFIG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure(CONFIG_FILE).build();
        Metadata metadata = new MetadataSources(serviceRegistry).getMetadataBuilder().build();
        SchemaExport export = getSchemaExport();
        dropDataBase(export, metadata);
        createDataBase(export, metadata);
        serviceRegistry.close();
        System.out.println("It's ok");
    }

    private static SchemaExport getSchemaExport() {
        SchemaExport export = new SchemaExport();
        File outputFile = new File(SCRIPT_FILE);
        String outputFilePath = outputFile.getAbsolutePath();
        export.setDelimiter(";");
        export.setOutputFile(outputFilePath);
        export.setHaltOnError(false);
        return export;
    }

    private static void dropDataBase(SchemaExport export, Metadata metadata) {
        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT, TargetType.STDOUT);
        export.drop(targetTypes, metadata);
    }

    private static void createDataBase(@NotNull SchemaExport export, Metadata metadata) {
        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.DATABASE, TargetType.SCRIPT, TargetType.STDOUT);
        SchemaExport.Action action = SchemaExport.Action.CREATE;
        export.execute(targetTypes, action, metadata);
    }
}
