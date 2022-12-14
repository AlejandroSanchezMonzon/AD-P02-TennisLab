/**
 * @author Mireya Sánchez Pinzón
 * @author Alejandro Sánchez Monzón
 */
package models

import org.hibernate.annotations.Type
import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "PEDIDO")
@NamedQuery(name = "Pedido.findAll", query = "SELECT p FROM Pedido p")
data class Pedido(
    @Id
    val id : Int,

    @Column(name = "uuid")
    @Type(type = "uuid-char")
    val uuid: UUID,

    val estado: TipoEstado,

    @OneToOne
    @JoinColumn(name = "encordador_id", referencedColumnName = "id", nullable = true)
    val encordador: Usuario,

    @Column(name = "fecha_tope")
    @Type(type = "org.hibernate.type.LocalDateType")
    val fechaTope: LocalDate,

    @Column(name = "fecha_entrada")
    @Type(type = "org.hibernate.type.LocalDateType")
    val fechaEntrada: LocalDate,

    @Column(name = "fecha_programada")
    @Type(type = "org.hibernate.type.LocalDateType")
    val fechaProgramada: LocalDate,

    @Column(name = "fecha_entrega")
    @Type(type = "org.hibernate.type.LocalDateType")
    val fechaEntrega: LocalDate,

    val precio: Float
)

enum class TipoEstado(estado: String) {
    RECIBIDO("RECIBIDO"),
    EN_PROCESO("EN_PROCESO"),
    TERMINADO("TERMINADO");

    /**
     * Función que parsea los Enums del objeto Pedido. Indicando su equivalencia al valor de tipo String.
     *
     * @param estado El enum en formato String equivalente al valor de la clase Enum.
     *
     * @throws IllegalArgumentException Excepción que el método lanzará si el String que trata de parsear no eixste, o el tipo no existe.
     *
     * @return TipoEstado, un enum que variará dependiendo del valor de dicha variable.
     */

    companion object {
        fun from(estado: String): TipoEstado {
            return when (estado.uppercase()) {
                "RECIBIDO" -> RECIBIDO
                "EN_PROCESO" -> EN_PROCESO
                "TERMINADO" -> TERMINADO
                else -> throw IllegalArgumentException("Estado no reconocido.")
            }
        }
    }
}