package com.gstu.pda;

import com.gstu.pda.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.*;

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
        init();
        fill();
    }

    private static void fill() {
        // copy xml file to resource folder and clear mvc deps
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        final SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        Class<?>[] classes = { SortsEntity.class, TagsEntity.class, TypesEntity.class, UnitsEntity.class };
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            for (Class<?> cl : classes) {
                for (int i = 0; i < 100; i++) {
                    final Object o = generateRandomEntityWithOnlyFieldName(cl);
                    session.save(o);
                }
            }
            session.getTransaction().commit();
        }
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            final List<DetailsEntity> details = createDetails(
                    getAll(session, SortsEntity.class),
                    getAll(session, TagsEntity.class),
                    getAll(session, TypesEntity.class),
                    getAll(session, UnitsEntity.class)
            );
            for (DetailsEntity detail : details) {
                session.save(detail);
            }
            session.getTransaction().commit();
        }
    }

    private static List<DetailsEntity> createDetails(List<SortsEntity> sorts, List<TagsEntity> tags,
                                               List<TypesEntity> types, List<UnitsEntity> units) {
        Random random = new Random(new Random().nextInt());
        final RandomString randomString = new RandomString(30, new SecureRandom(), RandomString.UPPER + RandomString.LOWER);
        List<DetailsEntity> de = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            DetailsEntity entity = new DetailsEntity();
            entity.setCode(1 + random.nextInt(Integer.MAX_VALUE));
            entity.setCount(1 + random.nextInt(Integer.MAX_VALUE));
            entity.setDescription(randomString.nextString());
            entity.setName(randomString.nextString());
            entity.setSorts(sorts.get(random.nextInt(sorts.size())));
            entity.setTags(tags.get(random.nextInt(tags.size())));
            entity.setTypes(types.get(random.nextInt(types.size())));
            entity.setUnits(units.get(random.nextInt(units.size())));
            de.add(entity);
        }
        return de;
    }

    private static <T> List<T> getAll(Session session, Class<T> entityClass) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(entityClass);
        cq.from(entityClass);
        return session.createQuery(cq).getResultList();
    }

    private static <T> T generateRandomEntityWithOnlyFieldName(Class<T> entityClass) {
        try {
            final T entity = entityClass.getConstructor().newInstance();
            final Field name = entityClass.getDeclaredField("name");
            name.setAccessible(true);
            name.set(entity, new RandomString().nextString());
            return entity;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException | NoSuchFieldException e) {
            return null;
        }
    }

    private static void init() {
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

    private static class RandomString {

        /**
         * Generate a random string.
         */
        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols[random.nextInt(symbols.length)];
            return new String(buf);
        }

        public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

        public static final String DIGITS = "0123456789";

        public static final String ALPHANUM = UPPER + LOWER + DIGITS;

        private final Random random;

        private final char[] symbols;

        private final char[] buf;

        public RandomString(int length, Random random, String symbols) {
            if (length < 1) throw new IllegalArgumentException();
            if (symbols.length() < 2) throw new IllegalArgumentException();
            this.random = Objects.requireNonNull(random);
            this.symbols = symbols.toCharArray();
            this.buf = new char[length];
        }

        /**
         * Create an alphanumeric string generator.
         */
        public RandomString(int length, Random random) {
            this(length, random, ALPHANUM);
        }

        /**
         * Create an alphanumeric strings from a secure generator.
         */
        public RandomString(int length) {
            this(length, new SecureRandom());
        }

        /**
         * Create session identifiers.
         */
        public RandomString() {
            this(21);
        }

    }
}
