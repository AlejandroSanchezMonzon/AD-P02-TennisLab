package repositories.usuario

import db.HibernateManager
import db.HibernateManager.manager
import models.Usuario
import mu.KotlinLogging
import javax.persistence.TypedQuery

private val logger = KotlinLogging.logger {}

class UsuariosRepository: IUsuariosRepository {
    override fun findAll(): List<Usuario> {
        logger.debug { "findAll()" }
        var usuarios = mutableListOf<Usuario>()
        HibernateManager.query {
            val query: TypedQuery<Usuario> = manager.createNamedQuery("Usuario.findAll", Usuario::class.java)
            usuarios = query.resultList
        }
        return usuarios
    }

    override fun findById(id: Int): Usuario? {
        logger.debug { "findById($id)" }
        var usuario: Usuario? = null
        HibernateManager.query {
            usuario = manager.find(Usuario::class.java, id)
        }
        return usuario
    }

    override fun save(entity: Usuario): Usuario {
        logger.debug { "save($entity)" }
        HibernateManager.transaction {
            manager.merge(entity)
        }
        return entity
    }

    override fun delete(entity: Usuario): Boolean {
        var result = false
        logger.debug { "delete($entity)" }
        HibernateManager.transaction {
            val usuario = manager.find(Usuario::class.java, entity.id)
            usuario?.let {
                manager.remove(it)
                result = true
            }
        }
        return result
    }
}