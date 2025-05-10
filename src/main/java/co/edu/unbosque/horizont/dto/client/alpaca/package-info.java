/**
 * Contiene los DTOs (Data Transfer Objects) necesarios para estructurar y transferir
 * la información de un usuario en el proceso de registro con la plataforma Alpaca.
 *
 * <p>Estos objetos representan los datos requeridos por Alpaca, incluyendo:</p>
 * <ul>
 *     <li>{@link co.edu.unbosque.horizont.dto.client.alpaca.AgreementDTO}: Acuerdos legales firmados por el usuario.</li>
 *     <li>{@link co.edu.unbosque.horizont.dto.client.alpaca.AlpacaAccountDTO}: Agregador principal que encapsula toda la información del usuario.</li>
 *     <li>{@link co.edu.unbosque.horizont.dto.client.alpaca.ContactDTO}: Información de contacto como dirección, ciudad y país.</li>
 *     <li>{@link co.edu.unbosque.horizont.dto.client.alpaca.DisclosuresDTO}: Declaraciones legales relacionadas con afiliaciones o exposiciones políticas.</li>
 *     <li>{@link co.edu.unbosque.horizont.dto.client.alpaca.IdentityDTO}: Datos personales e identificativos como nombre, fecha de nacimiento y país de ciudadanía.</li>
 * </ul>
 *
 * <p>Estos DTOs son utilizados para construir la carga útil que se envía a los servicios externos
 * de Alpaca como parte del flujo de integración para creación o validación de cuentas.</p>
 *
 * @see co.edu.unbosque.horizont.service.client.alpaca
 */
package co.edu.unbosque.horizont.dto.client.alpaca;