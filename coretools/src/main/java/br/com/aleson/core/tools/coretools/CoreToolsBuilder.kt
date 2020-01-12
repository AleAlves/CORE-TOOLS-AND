package br.com.aleson.core.tools.coretools

import br.com.aleson.core.tools.coretools.cryptography.CryptoImpl
import br.com.aleson.core.tools.coretools.retrofit.RetrofitCore

class CoreToolsBuilder constructor(url: String?) {

    var retrofit = url?.let { RetrofitCore(it) }

    var crypto = CryptoImpl()

    data class Builder(var url: String? = null) {
        fun server(url: String) = apply { this.url = url }
        fun build() = CoreToolsBuilder(url)
    }

}