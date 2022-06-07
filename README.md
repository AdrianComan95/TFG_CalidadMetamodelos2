# TFG - Validando y mejorando la calidad de metamodelos EMF 

### Autor: Adrian Vasile Coman --- Tutor: Esther Guerra Sánchez

**Resumen:**

Este Trabajo Fin de Grado surge de la necesidad de tener una aplicación software que sea capaz de comprobar diversos criterios de calidad sobre los metamodelos EMF, tales como errores de diseño, convenciones de nombres, buenas prácticas o métrica.
El paradigma de desarrollo de software usando modelos cada vez es más común, esto es debido a que aumenta la eficacia y también la calidad del software desarrollado, son el primer paso para el construir una aplicación y necesitamos que la calidad de esos modelos sea buena desde un primer momento. Es por eso que necesitamos herramientas que detecten errores en el modelo.
EMF es uno de los estándares de-facto para construir metamodelos dentro del ecosistema Eclipse. Para ello proporciona varios editores que validan si un metamodelo es estructuralmente valido, pero no comprueban ningún otro criterio de calidad.
La aplicación además de ser capaz de detectar si un metamodelo EMF cumple con los criterios de calidad también debe proporcionar (en el caso de que sea posible) uno o más quick-fixes que el usuario podrá aplicar para eliminar el problema.
Esta aplicación se usara a través de un menú contextual en el entorno de desarrollo integrado (IDE) Eclipse. El menú contextual se mostrará al clicar con el botón derecho del ratón sobre ficheros con extensión ecore.
Para poder usar dicho menú contextual deberemos instalar en Eclipse un Plugin que lo implementa, dicho Plugin será el objetivo final de este TFG.
