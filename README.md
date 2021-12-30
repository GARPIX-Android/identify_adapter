# Identify Adapter

Universal adapter for RecyclerView

## Requirements
minSdkVersion 23 (Android 6.0 MarshMallow)

## Features
Can be a generic reusable adapter for multiple RecyclerViews\
\
To use it, you just need to inherit all Data classes (whose objects will be processed in the adapter)
from the general interface Item, as well as create unique identifiers that describe the processing logic
each specific type of object\
\
An additional feature is easily adjustable vertical and horizontal margins with the ability to
set unique behavior for each type of object separately

## Install

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2. Add the dependency

```groovy
implementation 'com.github.GARPIX-Android:identify_adapter:1.0.1'
```

## Usage

1. Implement the required date classes from the Item interface

```kotlin
data class NewsItem(val title : String, val image : String) : Item

data class TitleItem(val title : String)  : Item

data class ImageItem (val url : String) : Item
```

2. Create layout for each class :

> `item_news.xml`
> `item_image.xml`
> `iten_title.xml`

3. Create identidier for each class, implements ItemIdentify interface\
Create ViewHolder extend BaseViewHolder for bind your data.\
You can read more about the implementation and purpose of the DiffUtil.ItemCallback methods here: [DiffUtil.ItemCallback](https://developer.android.com/reference/androidx/recyclerview/widget/DiffUtil.ItemCallback)

For example: 

>`IdentifyNews.kt`

```kotlin
class IdentifyNews : ItemIdentify<ItemNewsBinding, NewsItem> {

    override fun getDiffUtil() = object : DiffUtil.ItemCallback<NewsItem>() {

        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem) = oldItem.image == newItem.image

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem) = oldItem == newItem
    }

    override fun getLayoutId() = R.layout.item_news

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): BaseViewHolder<ItemNewsBinding, NewsItem> {
        val binding = ItemNewsBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun isRelativeItem(item: Item) = item is NewsItem
}

class NewsViewHolder(binding: ItemNewsBinding) : BaseViewHolder<ItemNewsBinding, NewsItem>(binding) {

    override fun onBind(item: NewsItem) {
        super.onBind(item)
        binding.title.text = item.title
        Glide.with(binding.root.context).load(item.image).into(binding.image)
    }

}
```
4.Where necessary, create and initialize the adapter, passing a list of all possible identifiers to it

>'TestingActivity.kt'

```kotlin
class TestingActivity : AppCompatActivity() {

  private lateinit var binding: ActivityTestingBinding
  private lateinit var adapter: IdentifyAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestingBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        adapter = IdentifyAdapter(
            listOf(
                IdentifyImage(),
                IdentifyText(),
                IdentifyNews()
            ) )
    }
   }
```
5. Set adapter and Layout Manager for your RecyclerView. Optionally install the necessary Dividers

>'TestingActivity.kt'

```kotlin
 with(binding.recycler) {
            layoutManager = LinearLayoutManager(this@TestingActivity)
            adapter = this@TestingActivity.adapter

            addItemDecoration(HorizontalDividerItemDecoration(100))
            addItemDecoration(VerticalItemDecoration(R.layout.item_text, 50, 50)) // optional
            addItemDecoration(VerticalItemDecoration(R.layout.item_picture, 50, 0)) // optional
            addItemDecoration(VerticalItemDecoration(R.layout.item_news, 50, 50)) // optional
        }
```
6. After receiving the data, submit it to the adapter:

>'TestingActivity.kt'

```kotlin
lifecycleScope.launchWhenStarted {
            viewModel.fetchData
                .collect {
                    adapter.submitList(it)
                }
        }
```
## License

```sh
MIT License

Copyright (c) 2021 GARPIX-Android

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
