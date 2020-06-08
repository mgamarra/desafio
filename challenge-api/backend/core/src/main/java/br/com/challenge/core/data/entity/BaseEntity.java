package br.com.challenge.core.data.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.OptimisticLockException;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.array.IntArrayType;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonNodeStringType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import lombok.Getter;
import lombok.Setter;

@Getter
@MappedSuperclass
@TypeDefs({
        @TypeDef(name = "string-array", typeClass = StringArrayType.class),
        @TypeDef(name = "int-array", typeClass = IntArrayType.class),
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class),
        @TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class),
        @TypeDef(name = "json-node", typeClass = JsonNodeStringType.class),
})
public abstract class BaseEntity {
    public abstract Serializable getId();

//    @Version
//    @Column(nullable = false)
//    @Setter
//    private Long version;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_at", nullable = false, updatable = false)
//    private Date createdAt;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "updated_at", nullable = false)
//    private Date updatedAt;

    /*
     * Private Methods
     */

//    @PrePersist
//    protected final void updateTimeInfoBeforePersist() {
//        this.createdAt = this.updatedAt = Calendar.getInstance().getTime();
//    }
//
//    @PreUpdate
//    protected final void updateTimeInfoBeforeUpdate() {
//        this.updatedAt = Calendar.getInstance().getTime();
//    }


    /**
     * Caso seja um registro existente, verifica a versão salva com a versão que está sendo passada para atualização
     * para garantir que sejam exatamente a mesma. Lança uma exceção caso contrario.
     * A propriedade version é controlada internamente pelo hibernate;
     * <p>
     * Esse assert deve ser obrigatoriamente executado por todos os objetos que extendem {@link BaseEntity}
     * durante a execução de algum método transacional que venha a alterar seu estado.
     */
//    public void assertVersion(Long incoming) {
//        if (incoming == null || (this.version != null && !this.version.equals(incoming)))
//            throw new OptimisticLockException();
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        BaseEntity that = (BaseEntity) o;
//
//        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
//    }
//
//    @Override
//    public int hashCode() {
//        return getId() != null ? getId().hashCode() : 0;
//    }
}
