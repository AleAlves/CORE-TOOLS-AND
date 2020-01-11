package br.com.aleson.core.tools.coretools

class CoreToolsBuilder constructor(url: String?) {

    var retrofit = url?.let { RetrofitCore(it) }

    data class Builder(var url: String? = null) {
        fun server(url: String) = apply { this.url = url }
        fun build() = CoreToolsBuilder(url)
    }

}