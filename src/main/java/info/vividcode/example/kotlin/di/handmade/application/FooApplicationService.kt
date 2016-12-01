package info.vividcode.example.kotlin.di.handmade.application

import info.vividcode.example.kotlin.di.handmade.domain.FooRepository

class FooApplicationService(private val requirements: FooApplicationService.Requirements) {

    /**
     * [FooApplicationService] クラスが必要とするオブジェクトをプロパティで列挙する。
     * このインターフェイスを実装したオブジェクトをコンストラクタで受け取ることで依存解決とする。
     * Minimal Cake Pattern に寄せてこうしてみた。
     * (Minimal Cake Pattern で言うところの UsersFooBar 相当。 より粒度は大きい。)
     *
     * 普通にコンストラクタに引数をずらずら書く方がいい気もする。
     */
    interface Requirements {
        val fooRepository: FooRepository
    }

}
