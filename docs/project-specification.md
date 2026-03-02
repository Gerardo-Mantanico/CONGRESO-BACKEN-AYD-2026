Descripción
La empresa de desarrollo de software “Code 'n Bugs” se especializa en desarrollos de software a la
medida, y usted, ha sido contratado para desarrollar un nuevo proyecto.
Uno de los clientes actuales desea crear una aplicación web dedicada a la gestión de congresos y
eventos académicos. La idea general de la aplicación es que sirva de plataforma para que los
organizadores de congresos de diferentes instituciones puedan administrar dichos eventos incluyendo
participantes, inscripciones, recepción de trabajos, diplomas de participación, entre otros. Y, los posibles
participantes podrán elegir los eventos a los que se quieran inscribir y participar.

Requisitos
La aplicación web debe ser una fuente de ingresos para nuestro cliente, por lo que el modelo de
negocios (la manera en que se gana dinero) es a través de los pagos que los participantes hacen al
inscribirse a un congreso. El sistema se queda con cierto porcentaje del pago y esa es la ganancia de
nuestro cliente.
Existen usuarios especiales que se encargan de planificar y crear congresos en el sistema. Todos los
congresos deben tener un precio mínimo de Q35.00.
Los participantes son todas las personas que se registran en el congreso. De cada uno de ellos se
almacenará información básica, que incluye foto, el nombre completo, la organización a la que
pertenece, el correo electrónico, el número de teléfono, el número de identificación personal —teniendo
en cuenta que pueden asistir participantes extranjeros y, por lo tanto, dicho número puede contener
letras.
Cualquier persona que desee ser participante de algún congreso debe de crear una cuenta en el sistema
en la cual se registren todos los datos básicos. Esta cuenta permite al usuario revisar los datos de todos
los congresos disponibles en el sistema, la cual debe incluir como mínimo la descripción básica, fecha
de inicio, precio, ubicación y el listado de las diferentes actividades.
Cabe destacar que los participantes de los congresos pueden ser asistentes, ponentes o talleristas, pero
pueden tener Múltiples tipos de participación, por ejemplo como asistentes y ponentes, o ponentes y
talleristas, etc.
Los administradores de cada congreso pueden abrir convocatorias para que los participantes que
deseen puedan proponer ponencias y/o talleres, estos trabajos serán evaluados por los miembros del
comité científico de cada congreso.

DIVISIÓN DE CIENCIAS DE LA INGENIERÍA
ANÁLISIS Y DISEÑO DE SISTEMAS 2
PROYECTO 1 A
PRIMER SEMESTRE 2026
Solo los trabajos aprobados pueden ser incluidos en el congreso, y si un trabajo es rechazado entonces
el participante puede volver a enviar otro trabajo siempre y cuando no esté cerrada la convocatoria.
El comité científico de cada congreso está definido por el administrador del congreso y deben ser usuarios
en el sistema.
Existen los ponentes invitados, los cuales no presentan trabajos ante el comité científico, pero deben ser usuarios
registrados dentro del sistema por los administradores del congreso si no tienen.
Todos los usuarios dentro del sistema pueden ser participantes de cualquier congreso.
Para el pago de las inscripciones se utiliza el crédito del usuario existente en la cartera digital de la
aplicación. Por simplicidad, esta cartera es recargada por el mismo usuario de forma manual indicando
la cantidad de dinero a acreditar.
Dado que los congresos son organizados por diferentes instituciones, estos deben poseer un registro en
el sistema de forma que solo los administradores de congresos enlazados a dichas instituciones puedan
registrar congresos.
Existen usuarios especiales para administrar el sistema como tal, y cambiar las configuraciones como
porcentaje de comisión por pago de inscripción, administrar instituciones, administrar usuarios.
Los usuarios administradores de congresos deben ser creados únicamente por administradores del
sistema.
Los usuarios no se eliminan, solo se desactivan. Y siempre debe existir al menos un administrador de
sistema activo.
Dado que los congresos se desarrollan en diferentes instalaciones, los administradores de congresos
deben definir los salones donde se llevarán a cabo las diferentes actividades del congreso. Los salones
deben ser asociados directamente con las actividades programadas. La definición de la información
específica que deba almacenarse en relación con cada salón quedará a discreción del estudiante,
siempre que esto permita una correcta identificación y gestión de los mismos.
El sistema deberá permitir a los administradores del congreso gestionar la información de los salones.
Esto incluye la posibilidad de registrar nuevos salones, modificar la información de los salones
existentes, consultar un listado general y eliminar un salón únicamente en el caso de que no tenga
actividades asociadas.

DIVISIÓN DE CIENCIAS DE LA INGENIERÍA
ANÁLISIS Y DISEÑO DE SISTEMAS 2
PROYECTO 1 A
PRIMER SEMESTRE 2026
Todo congreso tiene actividades, las cuales constituyen el núcleo del congreso y se desarrollan dentro
de los distintos salones disponibles. Cada actividad debe registrar como información básica: el nombre,
la descripción, el tipo de actividad (que puede ser PONENCIA o TALLER), la hora de inicio, la hora de
finalización y los participantes encargados de la misma. Los encargados de cada actividad serán el autor
del mismo o el ponente invitado.
En cuanto a las restricciones, la hora de inicio de una actividad no podrá ser posterior a la hora de
finalización. Asimismo, no podrán programarse dos actividades que ocurran de manera simultánea
dentro del mismo salón. Sin embargo, sí podrán desarrollarse varias actividades al mismo tiempo
siempre que se realicen en salones diferentes.
Los talleres son una actividad que cuenta con un cupo limitado de participantes, por lo que el sistema
debe controlar adecuadamente la capacidad máxima permitida para cada taller.
Los administradores del congreso pueden gestionar la información de las actividades del congreso. Esta
gestión incluye la posibilidad de editar los datos básicos de una actividad, tomando en consideración las
particularidades de las actividades de tipo TALLER, las cuales deben contar con un cupo limitado de
participantes. Como restricción, no será posible modificar el tipo de actividad una vez que haya sido
creada.
El administrador del congreso deberá tener la posibilidad de consultar un listado general de las
actividades registradas y de eliminar actividades cuando sea necesario. En este último caso, la
eliminación deberá realizarse en cascada, de manera que también se eliminan automáticamente las
asignaciones de los encargados vinculados a la actividad.
Para llevar un control del involucramiento de los asistentes (ponentes, talleristas, ponentes invitados y
asistentes) a las actividades del congreso, en la entrada de cada salón habrá un administrador del
congreso encargado de registrar la asistencia.
Para el registro de asistencia se solicitará al participante su número de identificación personal, y el
sistema deberá almacenar el registro correspondiente.
En el caso de las actividades de tipo TALLER, debido a su naturaleza y cupo limitado, únicamente podrá
registrarse la asistencia de aquellos participantes que hayan realizado previamente la reserva de su
cupo dentro del sistema.
Las asistencias registradas en el sistema tendrán carácter inalterable, por lo que no podrán ser
modificadas ni eliminadas bajo ninguna circunstancia, garantizando así la integridad de la información.
Finalmente, el proceso de toma de asistencias deberá ser ágil y cómodo para el usuario. Por esta razón,
se recomienda que la acción de asistencia del registrador esté disponible directamente desde el listado general
de actividades, evitando operaciones innecesarias.

DIVISIÓN DE CIENCIAS DE LA INGENIERÍA
ANÁLISIS Y DISEÑO DE SISTEMAS 2
PROYECTO 1 A
PRIMER SEMESTRE 2026
Ya que todos los congresos dan diploma de participación, cada asistente podrá encontrar su certificado
de participación a algún congreso al poder visitar dentro del sistema un apartado especial con el listado
de todos los congresos a los que se ha participante. Cabe destacar que solo en los congresos donde se
hayan registrado al menos 3 asistencias a actividades se tendrá acceso al diploma de participación.
Los ponentes, talleristas y ponentes invitados también reciben diploma por la actividad que se presenta.
Eso quiere decir que un participante puede tener múltiples diplomas por congreso.
Para efectos de calificación al momento de hacer un pago el usuario debe registrar la fecha de pago. Y
de igual manera cualquier operación que requiera fecha debe permitir que el usuario la indique de forma
manual.
Dependiendo del rol de usuario, así se tendrá acceso a los siguientes informes, los cuales deberán
presentarse en forma de tablas, con la posibilidad de aplicar filtros y de exportarse en formato HTML:
Un administrador del sistema puede ver los siguientes informes:
● Informe de ganancias en un intervalo de tiempo. Opcionalmente se puede filtrar por institución.
Este informe muestra el listado de congresos por cada institución, y por cada congreso debe
incluir datos pertinentes y el monto recaudado así como la ganancia recaudada. El informe
también debe incluir el total recaudado y el total de ganancia recaudada. Las instituciones deben
estar ordenadas de forma alfabética y los congresos deben estar ordenados de mayor a menor
en base a la ganancia recaudada.
● Informe de congresos por institución en un intervalo de tiempo. Este informe muestra por cada
institución el listado de congresos cuya fecha de inicio está dentro del intervalo de tiempo
indicado. se deben incluir los datos pertinentes de cada congreso
Un administrador de congresos puede ver los siguientes informes:
● Listado general de participantes: Debe contener: Número de identificación personal, nombre
completo, organización, correo electrónico, número de teléfono, tipos de participación (ponente,
tallerista, asistente, ponente invitado). Opcionalmente se puede filtrar por tipo de participante.
● Asistencia por actividad: Debe contener: nombre de la actividad, salón asignado, fecha y hora,
número de asistencias registradas. Opcionalmente se puede filtrar por actividad, por salón y por
rango de fechas.
● Reservas de talleres: Debe contener: nombre del taller, cupo total, número de reservas
realizadas, número de cupos disponibles, listado de participantes con reserva (Número de
identificación personal, nombre completo, correo electrónico, tipo de participante). Opcionalmente
se puede filtrar por taller.
● Informe de ganancias por congreso. Muestra un listado con información relevante de los
congresos junto con el total de dinero recaudado, el monto de comisión cobrado y la ganancia.

DIVISIÓN DE CIENCIAS DE LA INGENIERÍA
ANÁLISIS Y DISEÑO DE SISTEMAS 2
PROYECTO 1 A
PRIMER SEMESTRE 2026
Opcionalmente se puede filtrar por congreso y por rango de fecha donde se desarrollaron los
congresos.
Si no se especifica el intervalo de tiempo entonces se tomará en cuenta todos los registros.