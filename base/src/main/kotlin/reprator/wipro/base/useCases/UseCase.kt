package reprator.wipro.base.useCases

interface UseCase<Type, in Params> {
     suspend fun run(params: Params): AppResult<Type>
}