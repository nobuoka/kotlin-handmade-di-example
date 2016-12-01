package info.vividcode.example.kotlin.di.handmade.application

/**
 * このモジュールが他のモジュール (あるいはコンポーネント) に提供するものをプロパティで列挙します。
 * 実装は [Providers] です。
 * このモジュールのインターフェイス ([ApplicationModule]) と実装 ([Providers]) の組が Dagger における Module 相当になります。
 * (Dagger の Module より素朴な感じ。)
 * Minimal Cake Pattern で言うと MixInFooBar に相当します。 (Cake Pattern より粒度がでかい。)
 *
 * Dagger では実装がわかっている型については Module に書く必要はありませんでしたが、このパターンでは丁寧に
 * 書く必要があります。 (ちょっと面倒ではあるけど、Cake Pattern でもそこは一緒だしいいかなと思ってます。)
 */
interface ApplicationModule {

    val fooApplicationService: FooApplicationService

    val barApplicationService: BarApplicationService

    /**
     * [Providers] が実装を作る際に必要とするものをプロパティとして列挙します。
     * これらのプロパティは他のモジュール (どのモジュールかはわからない) で実装されることが期待されます。
     *
     * Minimal Cake Pattern で言うと UsersFooBar に相当します。 (Cake Pattern より粒度がでかい。)
     * Module が必要とするものについて Dagger は自動で見つけてくれるし、Cake Pattern でも実装側にミックスインすることで依存を表現
     * するので、それらと比べるとこうやって依存を明示するのは面倒ではあるなーと思いつつ、仕組み上ないとどうしようもないので
     * 諦めて書くことにしました。
     * モジュールが何を求めているのか一覧できるので、依存関係がわかりやすくなるのは利点かなと思っています。
     */
    interface Requirements :
            FooApplicationService.Requirements,
            BarApplicationService.Requirements

    /**
     * [ApplicationModule] で定義されているプロパティの実装を提供します。
     * コンストラクタが [Requirements] を [Lazy] で受け取るようにします。
     * この値を使って [ApplicationModule] が提供すべきオブジェクトを組み立てます。
     */
    class Providers(private val requirements: Lazy<Requirements>) : ApplicationModule {
        private val r by lazy { requirements.value }

        override val fooApplicationService by lazy { FooApplicationService(r) }
        override val barApplicationService by lazy { BarApplicationService(r) }
    }

}
