App: Esta aplicación calcula el precio final de una venta aplicando diferentes impuestos (IVA) según el país.
Brasil: 12% 
Chile: 19% 
Mexico: 16%

La aplicación se ejecuta con el archivo TestApplicatio.java

Controlador: api/sales/price
Llama al servicio correspondiente ejecutando el factory por país devolviendo de esta manera el precio total con IVA calculado.

Request para el controlador:
JSON: { "country": "CL", "amount": 100.0 }


Pruebas unitarias:

SaleControllerTest.java: Prueba que la API funcione correctamente.
- Para Brasil (BR), devuelve el precio correcto con IVA (12%)
- Para Chile (CL), devuelve el precio correcto con IVA (19%)  
- Para México (MX), devuelve el precio correcto con IVA (16%)
- Si envías un país que no existe (XX), devuelve un BadRequest

SaleDefaultMethodTest.java: Prueba el metodo por default que está en la interfaz

SaleFactoryTest.java: Prueba que se cree los objetos correctos según el país.

- Para Brasil, te da un objeto 'BrazilSale'
- Para Chile, te da un objeto 'ChileSale'
- Para México, te da un objeto 'MexicoSale'
- Si pides un país que no existe, lanza una excepción.

Ejecutar app: mvn spring-boot:run / desde el archivo TestApplication.java

Ejecutar test: mvn test / mvn test -Dtest=SaleControllerTest / desde el archivo test, "Run test"