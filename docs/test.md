# Guía para crear tests con Mock en Java usando Mockito

Este proyecto utiliza JUnit 5 y Mockito para realizar pruebas unitarias de los servicios. A continuación se explica cómo crear un test utilizando mocks, tomando como ejemplo el archivo `DriverServiceImplTest.java`.

## Requisitos
- JUnit 5
- Mockito

## Estructura básica de un test con Mock

1. **Anotar la clase de test:**
   ```java
   @ExtendWith(MockitoExtension.class)
   public class DriverServiceImplTest {
       // ...
   }
   ```
   Esto permite que Mockito gestione la inyección de dependencias.

2. **Definir los mocks y el objeto a probar:**
   ```java
   @Mock
   private DriverRepository repository;

   @InjectMocks
   private DriverServiceImpl driverService;
   ```
    - `@Mock`: Crea un mock del repositorio.
    - `@InjectMocks`: Inyecta los mocks en el servicio a probar.

3. **Preparar los datos y configurar el mock:**
   ```java
   NewDriverRequest newDriverRequest = new NewDriverRequest("Juanito Perez", 18);
   DriverEntity newDriverEntity = new DriverEntity();
   newDriverEntity.setName("Juanito Perez");
   newDriverEntity.setAge(18);
   when(repository.save(eq(newDriverEntity))).thenReturn(newDriverEntity);
   ```
    - `when(...).thenReturn(...)`: Configura el comportamiento del mock.

4. **Ejecutar el método a probar:**
   ```java
   DriverResponse result = driverService.create(newDriverRequest);
   ```

5. **Verificar el comportamiento y los resultados:**
   ```java
   ArgumentCaptor<DriverEntity> driverCapture = ArgumentCaptor.forClass(DriverEntity.class);
   assertAll(
       () -> verify(repository).save(driverCapture.capture()),
       () -> assertEquals("Juanito Perez", driverCapture.getValue().getName()),
       () -> assertEquals(18, driverCapture.getValue().getAge()),
       () -> assertEquals("Juanito Perez", result.getName()),
       () -> assertEquals(18, result.getAge())
   );
   ```
    - `verify(...)`: Verifica que el método fue llamado.
    - `assertEquals(...)`: Verifica los valores esperados.
    - `ArgumentCaptor`: Permite capturar el argumento pasado al mock.

## Ejemplo completo

```java
@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {
    @Mock
    private DriverRepository repository;
    @InjectMocks
    private DriverServiceImpl driverService;

    @Test
    void testCreateDriver() throws Exception {
        NewDriverRequest newDriverRequest = new NewDriverRequest("Juanito Perez", 18);
        ArgumentCaptor<DriverEntity> driverCapture = ArgumentCaptor.forClass(DriverEntity.class);

        DriverEntity newDriverEntity = new DriverEntity();
        newDriverEntity.setName("Juanito Perez");
        newDriverEntity.setAge(18);
        when(repository.save(eq(newDriverEntity))).thenReturn(newDriverEntity);

        DriverResponse result = driverService.create(newDriverRequest);

        assertAll(
            () -> verify(repository).save(driverCapture.capture()),
            () -> assertEquals("Juanito Perez", driverCapture.getValue().getName()),
            () -> assertEquals(18, driverCapture.getValue().getAge()),
            () -> assertEquals("Juanito Perez", result.getName()),
            () -> assertEquals(18, result.getAge())
        );
    }
}
```

## Consejos
- Usa `@Mock` para dependencias externas.
- Usa `@InjectMocks` para el objeto bajo prueba.
- Configura el comportamiento de los mocks con `when(...).thenReturn(...)`.
- Verifica interacciones con `verify(...)`.
- Usa `ArgumentCaptor` para inspeccionar argumentos.

## Referencias
- [Mockito Documentation](https://site.mockito.org/)
- [JUnit 5 User Guide](https://junit.org/junit5/)

---
Este README te ayudará a crear tests unitarios usando mocks en Java, siguiendo el patrón mostrado en el proyecto.
