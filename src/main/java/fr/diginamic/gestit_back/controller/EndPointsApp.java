package fr.diginamic.gestit_back.controller;

public class EndPointsApp {
      private static final String PROTOCOLE = "http://";
      private static final String DOMAIN_TEST = "localhost";
      private static final String PORT_TEST = ":8080";

      /*
       * Covoiturage =================================================================
       */
      public static final String COVOITURAGE_ENDPOINT = "/covoiturages";
      public static final String COVOITURAGE_URL = PROTOCOLE + DOMAIN_TEST + PORT_TEST + COVOITURAGE_ENDPOINT;
      public static final String COVOITURAGE_URN = DOMAIN_TEST + PORT_TEST + COVOITURAGE_ENDPOINT;

      /* getAllCovoiturages */
      public static final String COVOITURAGE_GET_ALL_RESOURCE = "/getAllCovoiturages";
      public static final String COVOITURAGE_GET_ALL_PATH_MVCTEST = COVOITURAGE_ENDPOINT + COVOITURAGE_GET_ALL_RESOURCE;
      public static final String COVOITURAGE_GET_ALL_URI = COVOITURAGE_URL + COVOITURAGE_GET_ALL_RESOURCE;

      /* create */
      public static final String COVOITURAGE_CREATE_RESOURCE = "/create";
      public static final String COVOITURAGE_CREATE_PATH_MVCTEST = COVOITURAGE_ENDPOINT + COVOITURAGE_CREATE_RESOURCE;
      public static final String COVOITURAGE_CREATE_URI = COVOITURAGE_URL + COVOITURAGE_CREATE_RESOURCE;

      /* pour tests */
      public static final String TEST_COVOITURAGE_GET_ALL_RESOURCE = "/TEST_getAllCovoiturages";
      public static final String TEST_COVOITURAGE_GET_ALL_PATH_MVCTEST = COVOITURAGE_ENDPOINT
                  + TEST_COVOITURAGE_GET_ALL_RESOURCE;
      public static final String TEST_COVOITURAGE_GET_ALL_URI = COVOITURAGE_URL + TEST_COVOITURAGE_GET_ALL_RESOURCE;
      public static final String TEST_COVOITURAGE_CREATE_RESOURCE = "/TEST_create";
      public static final String TEST_COVOITURAGE_CREATE_PATH_MVCTEST = COVOITURAGE_ENDPOINT
                  + TEST_COVOITURAGE_CREATE_RESOURCE;
      public static final String TEST_COVOITURAGE_CREATE_URI = COVOITURAGE_URL + TEST_COVOITURAGE_CREATE_RESOURCE;
      /*
       * =============================================================================
       */

}
